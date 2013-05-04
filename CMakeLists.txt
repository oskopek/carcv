#Minimal CMake version required
cmake_minimum_required(VERSION 2.8)

#Name of the project
project( CARCV )

#Required lib
find_package( OpenCV REQUIRED )

#List of subdirectories to build
add_subdirectory(src)
add_subdirectory(cartest)
add_subdirectory(rectangles)
add_subdirectory(facedetect)
add_subdirectory(cardetect)
add_subdirectory(example_cardetect)
add_subdirectory(carrect)
add_subdirectory(templatematching)
