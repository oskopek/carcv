'''
/*
 * Copyright 2012-2014 CarCV Development Team
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
'''

import numpy as np
import cv2
import sys

img = cv2.imread("train.jpg") #Loads up image with training numbers
im3 = img.copy() #Makes copy of original image for output purposes

gray = cv2.cvtColor(img,cv2.COLOR_BGR2GRAY) #Switches out black and white
blur = cv2.GaussianBlur(gray,(5,5),0) #Smoothens out the image
thresh = cv2.adaptiveThreshold(blur,255,1,1,11,2) #Builds a threshold making a binary image
#################Now comes the fun part, Actual digit training##########

contours,hierarchy = cv2.findContours(thresh,cv2.RETR_LIST,cv2.CHAIN_APPROX_SIMPLE)
#Finds contours

samples =  np.empty((0,100)) #empty sample array
responses = [] #empty respones array
keys = [i for i in range(48,150)] #Keys that will be pressed, 48-57 keys are numbers from 0-9 and from 65 to 90 A-Z characters


for cnt in contours: #Goes through all contours on picture
    if cv2.contourArea(cnt)>100 and cv2.contourArea(cnt)<1000: #Excludes all mistakes, by making area large enough
        [x,y,w,h] = cv2.boundingRect(cnt)#Sets default values for a contour binding box

        if  h>25: #If it is actual letter
            cv2.rectangle(img,(x,y),(x+w,y+h),(0,0,255),2)
##            Draws a recangle on original image with point (x,y), and other point
##            (x+w,y+h) which is red and has line thick 2 pixels
            roi = thresh[y:y+h,x:x+w] #Makes stable threshold at that rectangle (No other character can interfere with previous one)
            roismall = cv2.resize(roi,(10,10)) #Resizes it to 10x10
            cv2.imshow('norm',img) #Shows new image
            key = cv2.waitKey(0)

            if key == 27: #Esc key
                sys.exit()
            elif key in keys: #Key is from range 0-9 and A-Z
                responses.append(key) #appends the value of the key to responses
                sample = roismall.reshape((1,100)) #makes a column out of samples
                samples = np.append(samples,sample,0) #Makes final list of samples

responses = np.array(responses)
responses = responses.reshape((responses.size,1))#makes a column out of responses
print "training complete"

np.savetxt('generalsamples.data',samples)
np.savetxt('generalresponses.data',responses)
#Saves samples and responses into datafiles

cv2.waitKey(0)
cv2.destroyAllWindows()