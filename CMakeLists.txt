cmake_minimum_required(VERSION 3.9)
project(SimpleArithmeticServer)

set(CMAKE_CXX_STANDARD 14)

add_executable(Client client.cpp)
add_executable(Server server.cpp)