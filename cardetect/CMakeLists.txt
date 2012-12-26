include_directories(${CARCV_SOURCE_DIR}/)
link_directories(${CARCV_BINARY_DIR}/)

#
add_executable(cardtc cardetect.cpp)
add_executable(carrect carrectangles.cpp)
target_link_libraries( cardtc ${OpenCV_LIBS} /lib/libm.so)
target_link_libraries( carrect ${OpenCV_LIBS} /lib/libm.so)