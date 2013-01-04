include_directories(${CARCV_SOURCE_DIR}/)
link_directories(${CARCV_BINARY_DIR}/)

add_executable(carcv carcv.cpp carcv.hpp match.cpp match.hpp carimg.cpp carimg.hpp det.cpp det.hpp)


target_link_libraries( carcv ${OpenCV_LIBS} /lib/libm.so /lib/libboost_system.so /lib/libboost_filesystem.so)
