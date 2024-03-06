import cv2
import time
from pose_detector import PoseDetector
import mediapipe as mp

pTime = 0
# capture from webcam
# cap = cv2.VideoCapture(1)
# capture from video file
cap = cv2.VideoCapture("input.mp4")

detector = PoseDetector()

while(cap.isOpened()):
    success, img = cap.read()
    
    if success == False:
        break
    
    img, p_landmarks, p_connections = detector.findPose(img, False)
    
    #print(p_landmarks.landmark[0])
    
    # draw points
    mp.solutions.drawing_utils.draw_landmarks(img, p_landmarks, p_connections)
    lmList = detector.getPosition(img)
    if len(lmList) > 10:
        cv2.circle(img,(lmList[10][1], lmList[10][2]),10,(0,0,255),3)
    #spot = lmList[0]
    #

    cTime = time.time()
    fps = 1 / (cTime - pTime)
    pTime = cTime

    cv2.imshow("Image", img)
    cv2.waitKey(1)

cap.release()
cv2.destroyAllWindows()
