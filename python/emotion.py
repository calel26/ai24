import cv2
from deepface import DeepFace

# Initialize the Haar Cascade face detection model
face_cascade = cv2.CascadeClassifier(
    cv2.data.haarcascades + 'haarcascade_frontalface_default.xml')


# Initialize webcam
cap = cv2.VideoCapture(1)


while True:
    ret, frame = cap.read()
    if not ret:
        break

    # Convert the frame to grayscale (required by Haar Cascades)
    gray = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)
    # storing the result

    # Detect faces in the frame
    faces = face_cascade.detectMultiScale(
        gray, scaleFactor=1.1, minNeighbors=5, minSize=(30, 30), flags=cv2.CASCADE_SCALE_IMAGE)

    for (x, y, w, h) in faces:
        # Draw a rectangle around the face
        cv2.rectangle(frame, (x, y), (x+w, y+h), (0, 255, 0), 2)
        face_frame = frame[y:y+h, x:x+w]

        # Perform emotion analysis on the cropped face
        try:
            result = DeepFace.analyze(
                face_frame, actions=['emotion', "age"], enforce_detection=False)
            # Assuming result is the variable that holds the list of dictionaries
            for face_analysis in result:
                dominant_emotion = face_analysis['dominant_emotion']
                age = face_analysis['age']
                info = dominant_emotion + ' ' + str(age)
                # print("Dominant Emotion:", dominant_emotion)
                font = cv2.FONT_HERSHEY_SIMPLEX
                frame = cv2.putText(frame, info, (x, y-10),
                                    font, 0.5, (0, 0, 255), 2, cv2.LINE_AA)

        except Exception as e:
            print(f"Error in DeepFace analysis: {e}")

    # Display the resulting frame
    cv2.imshow('Video', frame)

    if cv2.waitKey(1) & 0xFF == ord('q'):
        break

cap.release()
cv2.destroyAllWindows()
