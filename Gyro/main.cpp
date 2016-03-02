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
    gyro->write_zerocalibrate();
    
    if (!gyro->write_setup()) {
        printf("couldn't write setup");
        return 0;
    }
    
    float sx = 0, sy = 0, sz = 0;
    float x, y, z;
    
    myled = 0;
    
    float e = 2;
    
  while(1) {
      myled = !myled;
      gyro->read_gyroscope(&x, &y, &z);
//      printf("%f\t%f\t%f\r\n", x, y, z);
      if (x > e || x < -e) {
          sx += x / 10;
      }
      if (y > e || y < -e) {
          sy += y / 10;
      }
      if (z > e || z < -e) {
          sz += z / 10;
      }
      
      printf("rotation X: %f\t\tY : %f\t\tZ: %f\r\n", sx, sy, sz);
      wait(0.1);
  }
}
 