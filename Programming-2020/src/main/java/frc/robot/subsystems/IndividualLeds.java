package frc.robot.subsystems;

import java.util.Arrays;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;

public class IndividualLeds{
    AddressableLEDBuffer ledBuffer;
    AddressableLED led;
    int ledCounter;
    public IndividualLeds() {
        // PWM port 9
        // Must be a PWM header, not MXP or DIO
        led = new AddressableLED(9);
        // Reuse buffer
        // Default to a length of 60, start empty output
        // Length is expensive to set, so only set it once, then just update data
        ledBuffer = new AddressableLEDBuffer(60);
        led.setLength(ledBuffer.getLength());
    }

    public void changeAllColors(int r, int g, int b){
        for (var i = 0; i < ledBuffer.getLength(); i++) {
            // Sets the specified LED to the RGB values for red
            ledBuffer.setRGB(i, r,g,b);
        }
    
        led.setData(ledBuffer);
        led.start();
    }

    public void alternateColors(int r1, int g1, int b1, int r2, int g2, int b2){
       for (var i = 0; i < ledBuffer.getLength(); i++) {
           if (i%2==0){
                // Sets the specified LED to the RGB values for red
                ledBuffer.setRGB(i, r1,g1,b1);
           }else {
            ledBuffer.setRGB(i, r2,g2,b2);
           }
       }
   
       led.setData(ledBuffer);
       led.start();
   }

   public int getN(int n){ 
       return (ledCounter + n) % (ledBuffer.getLength());
   }

   public static boolean contains(final int[] arr, final int key) {
    return Arrays.stream(arr).anyMatch(i -> i == key);
    }

   public void scrollNColorsWithBackround(int r, int g, int b,int r1, int g1, int b1, int n){
    ledCounter = getN(1);
    int[] intArray = new int[n];
    for (var i = 0; i < n; i++){
        intArray[i] =getN(i+1);
    }
    // build an array of length N that has all of the values of getN(1) to getN(n)
    // build arrays and check if numbers are in arrays
    // check the array in the function scrollN then build an array based of the variable n to send info to an array
    for (var i = 0; i < ledBuffer.getLength(); i++) {
             // Sets the specified LED to the RGB values for red
        if (contains(intArray, i)){
            if(i%2==0){
             ledBuffer.setRGB(i, r,g,b);
            }else{
              ledBuffer.setRGB(i, 0,0,0);
            }
        }else {
         ledBuffer.setRGB(i,r1,g1,b1);
        }
    }
   
    led.setData(ledBuffer);
    led.start();
   }
    

   public void scrollColor(int r, int g, int b){
    ledCounter = (ledCounter + 1) % (ledBuffer.getLength());
    int ledCounter1 = (ledCounter + 2) % (ledBuffer.getLength());
    int ledCounter2 = (ledCounter + 3) % (ledBuffer.getLength());
    int ledCounter3 = (ledCounter + 4) % (ledBuffer.getLength());


    for (var i = 0; i < ledBuffer.getLength(); i++) {
             // Sets the specified LED to the RGB values for red
        if (i == ledCounter || i == ledCounter1 || i == ledCounter2 || i == ledCounter3){
             ledBuffer.setRGB(i, r,g,b);
        }else {
         ledBuffer.setRGB(i,0,0,0);
        }
    }
   
    led.setData(ledBuffer);
    led.start();
   }
    
}