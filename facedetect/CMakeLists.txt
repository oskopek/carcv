include_directories(${CARCV_SOURCE_DIR}/)
link_directories(${CARCV_BINARY_DIR}/)

#
add_executable(facedtc facedetect.cpp)
target_link_libraries( facedtc ${OpenCV_LIBS} /lib/libm.so)
