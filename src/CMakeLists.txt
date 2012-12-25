include_directories(${CARCV_SOURCE_DIR}/)
link_directories(${CARCV_BINARY_DIR}/)

#
add_executable(Car Car.cpp)
target_link_libraries( Car ${OpenCV_LIBS} )
