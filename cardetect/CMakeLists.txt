#
include_directories(${CARCV_SOURCE_DIR}/)
link_directories(${CARCV_BINARY_DIR}/)

#Add executable carcv, with sources...
add_executable(carcv carimg.hpp carimg.cpp tools.hpp tools.cpp fileio.hpp fileio.cpp match.cpp match.hpp detection.hpp detection.cpp carcv.hpp carcv.cpp main.cpp)

#Link with libraries OpenCV (Excluded: /lib/libm.so)
# all OpenCV libs
# boost: filesystem, system
target_link_libraries( carcv ${OpenCV_LIBS} /lib/libboost_system.so /lib/libboost_filesystem.so)