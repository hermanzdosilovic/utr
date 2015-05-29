################################################################################
# Automatically-generated file. Do not edit!
################################################################################

# Add inputs and outputs from these tool invocations to the build variables 
CPP_SRCS += \
../src/SimPa.cpp \
../src/dpa_definition.cpp \
../src/state.cpp \
../src/string.cpp \
../src/symbol.cpp 

OBJS += \
./src/SimPa.o \
./src/dpa_definition.o \
./src/state.o \
./src/string.o \
./src/symbol.o 

CPP_DEPS += \
./src/SimPa.d \
./src/dpa_definition.d \
./src/state.d \
./src/string.d \
./src/symbol.d 


# Each subdirectory must supply rules for building sources it contributes
src/%.o: ../src/%.cpp
	@echo 'Building file: $<'
	@echo 'Invoking: GCC C++ Compiler'
	g++ -std=c++0x -O2 -g3 -pedantic -pedantic-errors -w -Wall -Wextra -Werror -Wconversion -c -fmessage-length=0 -MMD -MP -MF"$(@:%.o=%.d)" -MT"$(@:%.o=%.d)" -o "$@" "$<"
	@echo 'Finished building: $<'
	@echo ' '


