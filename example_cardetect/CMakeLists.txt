include_directories(${CARCV_SOURCE_DIR}/)
link_directories(${CARCV_BINARY_DIR}/)

add_executable(cardtc cardetect)
target_link_libraries( cardtc ${OpenCV_LIBS} /lib/libm.so)
