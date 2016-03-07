#include "mbed.h"
#include "grove_gyroscope_class.h"

//------------------------------------
// Hyperterminal configuration
// 9600 bauds, 8-bit data, no parity
//------------------------------------

DigitalOut myled(LED1);
DigitalIn sda(D14);
DigitalIn scl(D15);


int main() {
    
    GroveGyroscope *gyro = new GroveGyroscope(D14, D15);
    if (!gyro->write_setup()) {
        printf("couldn't write setup\r\n");
        return 0;
    }
	gyro->write_zerocalibrate();
	printf("gyro setup done\r\n");
    
    float sx = 0, sy = 0, sz = 0;
    float x, y, z;
    
    myled = 0;
    
    float e = 2;
    
	Timer timer;
	timer.start();
	while(1) {
		float start = timer.read();
		myled = !myled;
		gyro->read_gyroscope(&x, &y, &z);
		
		sx += x;
		sy += y;
		sz += z;
		
		printf("%8.3f  %8.3f %8.3f\t\t\t %8.3f  %8.3f  %8.3f\r\n", x, y, z, sx, sy, sz);
		
		//measure the loop iteration time to establish the fixed loop rate (100 ms for example)
		//otherwise it seems that gyro doesn't work quite well.. for now
		float timeTaken = timer.read() - start;
		wait(0.1 - timeTaken);
	}
}
 