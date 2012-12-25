include_directories(${CARCV_SOURCE_DIR}/)
link_directories(${CARCV_BINARY_DIR}/)

#
add_executable(Rect recttest.cpp)
target_link_libraries( Rect ${OpenCV_LIBS} )
