import cv2
import numpy as np
import insightface
import requests
import os


def load_reference_embeddings(model, reference_images_path):
    reference_embeddings = {}
    for image_name in os.listdir(reference_images_path):
        if not image_name.lower().endswith(('.png', '.jpg', '.jpeg')):
            continue  # Skip non-image files
        person_name = os.path.splitext(image_name)[0]
        image_path = os.path.join(reference_images_path, image_name)
        embedding = load_reference_embedding(model, image_path)
        if embedding is not None:
            reference_embeddings[person_name] = embedding
    return reference_embeddings


def load_reference_embedding(model, reference_image_path):
    # Load reference image and compute embedding
    ref_img = cv2.imread(reference_image_path)
    ref_faces = model.get(ref_img)
    if len(ref_faces) > 0:
        # Assuming the first face is the reference face
        return ref_faces[0].embedding
    else:
        print("No face detected in the reference image.")
        return None


def find_matching_face(detected_faces, ref_embedding):
    # Threshold for cosine similarity
    # Adjust based on your requirements
    threshold = 0.4
    for face in detected_faces:
        # Compute cosine similarity
        similarity = np.dot(face.embedding, ref_embedding) / \
            (np.linalg.norm(face.embedding) * np.linalg.norm(ref_embedding))
        if similarity > threshold:
            return True, face.bbox
    return False, None


def find_matching_faces(detected_faces, reference_embeddings):
    threshold = 0.4  # Cosine similarity threshold
    best_match = None
    highest_similarity = threshold  # Start with threshold to filter lower matches

    for face in detected_faces:
        for person_name, ref_embedding in reference_embeddings.items():
            similarity = np.dot(face.embedding, ref_embedding) / \
                (np.linalg.norm(face.embedding) * np.linalg.norm(ref_embedding))
            if similarity > highest_similarity:
                highest_similarity = similarity
                best_match = (person_name, face.bbox)

    return best_match


# Initialize the InsightFace model
model = insightface.app.FaceAnalysis()
# ctx_id = -1 for CPU, use appropriate ctx_id for GPU
# model.prepare(ctx_id=-1, nms=0.4)
model.prepare(ctx_id=0)

# Assuming '/path/to/reference_images/' contains your reference images
reference_images_path = "./faces/"
reference_embeddings = load_reference_embeddings(model, reference_images_path)
# Initialize webcam
cap = cv2.VideoCapture(1)

frame_count = 0
process_every_n_frames = 2  # Adjust based on performance

seen = []

while True:
    ret, frame = cap.read()
    # frame_small = cv2.resize(frame, (0, 0), fx=0.5, fy=0.5)
    if not ret:
        print("Failed to grab frame")
        break

    frame_count += 1
    if frame_count % process_every_n_frames != 0:
        continue  # Skip the frame
    # Detect faces in the frame
    faces = model.get(frame)

    # Find matching face
    match = find_matching_faces(faces, reference_embeddings)
    if match:
        person_name, bbox = match
        if person_name not in seen:
            seen.append(person_name)
            requests.post(
                os.environ["NTFY_TOPIC"],
                data="just saw " + person_name,
                auth=(os.environ["NTFY_USER"], os.environ["NTFY_KEY"])
            )
        # print(person_name
        # Draw bounding box and person's name
        cv2.rectangle(frame, (int(bbox[0]), int(bbox[1])), (int(
            bbox[2]), int(bbox[3])), (0, 255, 0), 2)
        cv2.putText(frame, person_name, (int(bbox[0]), int(
            bbox[1]-10)), cv2.FONT_HERSHEY_SIMPLEX, 0.9, (0, 255, 0), 2)
    else:
        cv2.putText(frame, "No Match", (50, 50),
                    cv2.FONT_HERSHEY_SIMPLEX, 0.9, (0, 0, 255), 2)

    # Display the resulting frame
    cv2.imshow('Frame', frame)

    # Break the loop with the 'q' key
    if cv2.waitKey(1) & 0xFF == ord('q'):
        break

# Release the capture
cap.release()
cv2.destroyAllWindows()
