#
include_directories(${CARCV_SOURCE_DIR}/)
link_directories(${CARCV_BINARY_DIR}/)

#Add executable carcv, with sources...
add_executable(carcv carcv.cpp carcv.hpp match.cpp match.hpp carimg.cpp carimg.hpp det.cpp det.hpp)

#Link with libraries OpenCV (Excluded: /lib/libm.so)
# all OpenCV libs
# boost: filesystem, system
target_link_libraries( carcv ${OpenCV_LIBS} /lib/libboost_system.so /lib/libboost_filesystem.so)