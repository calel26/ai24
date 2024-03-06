import cv2
import dlib
import numpy as np

# Initialize Dlib's face detector and landmark predictor
detector = dlib.get_frontal_face_detector()
predictor = dlib.shape_predictor("shape_predictor_68_face_landmarks.dat")

cap = cv2.VideoCapture(1)
taken = False

while True:
    _, frame = cap.read()
    gray = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)
    faces = detector(gray)
    blank_image = np.zeros((frame.shape[0], frame.shape[1], 3), np.uint8)

    for face in faces:
        landmarks = predictor(gray, face)

        minx, miny, maxx, maxy = float("inf"), float("inf"), 0, 0
        # Loop through each landmark point around the mouth
        for n in range(1, 68):  # Dlib's mouth landmarks are from point 48 to 67
            x = landmarks.part(n).x
            y = landmarks.part(n).y
            color = (255, 0, 0)
            if 48 <= n <= 67:
                color = (0, 255, 0)
            else:
                continue
            cv2.circle(blank_image, (x, y), 2, color, -1)
            if x > maxx:
                maxx = x
            if y > maxy:
                maxy = y
            if x < minx:
                minx = x
            if y < miny:
                miny = y

            mouth_aspect_ratio = (maxy - miny) / (maxy - minx)
            print(mouth_aspect_ratio)
            if mouth_aspect_ratio < -0.4 and not taken:
                # surprise
                taken = True
                cv2.imwrite("./surprise.png", frame)

    cv2.imshow("Frame", blank_image)

    if cv2.waitKey(1) & 0xFF == ord('q'):
        break

cap.release()
cv2.destroyAllWindows()
