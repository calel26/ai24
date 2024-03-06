import cv2
import numpy as np
from deepface import DeepFace


# Load the Haar Cascades
face_cascade = cv2.CascadeClassifier(
    cv2.data.haarcascades + 'haarcascade_frontalface_default.xml')
eye_cascade = cv2.CascadeClassifier(
    cv2.data.haarcascades + 'haarcascade_eye.xml')
mouth_cascade = cv2.CascadeClassifier(
    'haarcascade_smile.xml')  # Update the path accordingly


cap = cv2.VideoCapture(1)  # Initialize webcam

surprise = False

while True:
    ret, frame = cap.read()
    if not ret:
        break

    gray = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)
    faces = face_cascade.detectMultiScale(gray, 1.3, 5)
    # create a new image
    # blank_image = np.zeros((frame.shape[0], frame.shape[1], 3), np.uint8)

    for (x, y, w, h) in faces:
        frame = cv2.rectangle(
            frame, (x, y), (x+w, y+h), (255, 0, 0), 2)
        roi_gray = gray[y:y+h, x:x+w]
        roi_color = frame[y:y+h, x:x+w]

        # Detect eyes within the face region
        eyes = eye_cascade.detectMultiScale(roi_gray)
        for (ex, ey, ew, eh) in eyes:
            frame = cv2.rectangle(frame, (x + ex, y + ey),
                                  ((x + (ex+ew)), (y + (ey+eh))), (0, 255, 0), 2)

        # Detect mouth within the face region
        mouths = mouth_cascade.detectMultiScale(roi_gray, 1.7, 11)
        for (mx, my, mw, mh) in mouths:
            frame = cv2.rectangle(frame, (x + mx, y + my),
                                  (x + mx+mw, y + my+mh), (255, 0, 255), 2)
            # detect surprise
            if mh > mw and not surprise:
                surprise = True
                cv2.imwrite("surprisedface.png", frame)

        

    cv2.imshow('frame', frame)
    if cv2.waitKey(1) & 0xFF == ord('q'):
        break

cap.release()
cv2.destroyAllWindows()
