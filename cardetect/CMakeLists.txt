include_directories(${CARCV_SOURCE_DIR}/)
link_directories(${CARCV_BINARY_DIR}/)

#
add_executable(carrect carrectangles carmatcher)
add_executable(carcv carcv.cpp carcv.hpp carmatcher.cpp carmatcher.hpp carimg.cpp carimg.hpp)
add_executable(cardtc cardetect)
target_link_libraries( cardtc ${OpenCV_LIBS} /lib/libm.so)
target_link_libraries( carrect ${OpenCV_LIBS} /lib/libm.so /lib/libboost_system.so /lib/libboost_filesystem.so)
target_link_libraries( carcv ${OpenCV_LIBS} /lib/libm.so /lib/libboost_system.so /lib/libboost_filesystem.so)
