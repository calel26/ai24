import cv2

# Initialize the camera (0 is the default camera)
cap = cv2.VideoCapture(1)

# Check if the camera opened successfully
if not cap.isOpened():
    print("Error: Could not open camera.")
    exit()

target_color = (255, 0, 0)
scale_factor = 0.25

try:
    while True:
        # Capture frame-by-frame
        ret, frame = cap.read()

        oframe = frame
        frame = cv2.resize(frame,
                           None,
                           fx=scale_factor,
                           fy=scale_factor,
                           interpolation=cv2.INTER_AREA)

        # If frame is read correctly, ret is True
        if not ret:
            print("Error: Can't receive frame (stream end?). Exiting ...")
            break

        width, height = frame.shape[0:2]

        tb, tg, tr = target_color

        max_red = None
        red_dist = float('inf')

        for x in range(width):
            for y in range(height):
                b, g, r = frame[x][y]
                d = (r-tr)**2 + (g-tg)**2 + (b-tb)**2
                if red_dist > d:
                    max_red = x, y
                    red_dist = d

        ox, oy = max_red
        max_red = int(oy / scale_factor), int(ox / scale_factor)

        cv2.circle(oframe, max_red, 10, (0, 0, 255), 2)
        cv2.imshow("Color Following", oframe)

        # Break the loop when 'q' is pressed
        if cv2.waitKey(1) == ord('q'):
            break
finally:
    # When everything done, release the capture
    cap.release()
    cv2.destroyAllWindows()
