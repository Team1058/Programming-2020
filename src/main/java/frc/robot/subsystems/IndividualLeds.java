package frc.robot.subsystems;

import java.util.Arrays;

//import org.graalvm.compiler.core.common.type.ArithmeticOpTable.BinaryOp.And;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.XboxController;

public class IndividualLeds{
    AddressableLEDBuffer ledBuffer;
    AddressableLED led;
    int ledCounter = 0;
    int direction = 1;
    int X = 0;
    double ledSides;
    private XboxController gamepad = new XboxController(0);
    
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

    public void changeAllColors(int r, int g, int b) {
        for (var i = 0; i < ledBuffer.getLength(); i++) {
            // Sets the specified LED to the RGB values for red
            ledBuffer.setRGB(i, r,g,b);
        }
    
        led.setData(ledBuffer);
        led.start();
    }

    public void alternateColors(int r1, int g1, int b1, int r2, int g2, int b2) {
       for (var i = 0; i < ledBuffer.getLength(); i++) {
           if (i%2==0){
                // Sets the specified LED to the RGB values for red
                ledBuffer.setRGB(i, r1,g1,b1);
           } else {
            ledBuffer.setRGB(i, r2,g2,b2);
           }
       }

       led.setData(ledBuffer);
       led.start();
   }

   
    public int getN(int n) { 
        return (ledCounter + n) % (ledBuffer.getLength());
    }


    public static boolean contains(final int[] arr, final int key) {
        return Arrays.stream(arr).anyMatch(i -> i == key);
    }


    public void backandforth(int r,int g,int b, int r2, int g2, int b2, int scrollWidth){
        if (ledCounter + scrollWidth  == ledBuffer.getLength()){
            direction = -1;
        } else if (ledCounter == 0){
            direction = 1;
        }
        int[] intArray = new int[scrollWidth];
        for (var i = 0; i < scrollWidth; i++) {
            intArray[i] = ledCounter + i;
        }
        for (var i = 0; i < ledBuffer.getLength(); i++) {
            if (contains(intArray, i)) {
                ledBuffer.setRGB(i,r,g,b);
            }else{
                ledBuffer.setRGB(i,r2,g2,b2);
            }
        }
        ledCounter += (direction * 1);
        led.setData(ledBuffer);
        led.start();
    }

    public void holygrailbackandforth(int r1,int g1,int b1,int r2,int g2,int b2,int r3,int g3,int b3,int r4,int g4,int b4,int scrollWidth,double squish,int X,int Y){
        if (ledCounter + scrollWidth * squish  == ledBuffer.getLength()){
            direction = -1;
        } else if (ledCounter + scrollWidth * (1 - squish)  == 0 ){
            direction = 1;
        }
        int[] intArray = new int[scrollWidth];
        for (var i = 0; i < scrollWidth; i++) {
            intArray[i] = ledCounter + i;
        }
        for (var i = 0; i < ledBuffer.getLength(); i++) {
            if (contains(intArray, i)&&(i%X==Y)) {
                ledBuffer.setRGB(i,r1,g1,b1);
            }else{
                ledBuffer.setRGB(i,r2,g2,b2);
            }   
            if (!contains(intArray, i)){
                ledBuffer.setRGB(i, r3, g3, b3);
                System.out.println("BYE");
            }
            if (!contains(intArray, i)&&(i%X==Y)){
                ledBuffer.setRGB(i, r4, g4, b4);
                System.out.println("HI");
            }
        }
        ledCounter += (direction * 1);
        led.setData(ledBuffer);
        led.start();
    }

    public void scrollNColorsWithBackround(int r, int g, int b, int r1, int g1, int b1,int r2, int g2, int b2, int n, int x, int y) {
        ledCounter = getN(1);
        int[] intArray = new int[n];
        for (var i = 0; i < n; i++) {
            intArray[i] =getN(i+1);
        }
        // build an array of length N that has all of the values of getN(1) to getN(n)
        // build arrays and check if numbers are in arrays
        // check the array in the function scrollN then build an array based of the variable n to send info to an array
        for (var i = 60; i < ledBuffer.getLength(); i--) {
                // Sets the specified LED to the RGB values for red
            if (contains(intArray, i)) {
                if (i%x == y) {
                ledBuffer.setRGB(i, r,g,b);
                } else {
                ledBuffer.setRGB(i, r1,g1,b1);
                }
            } else {
                if (i%2==0){
                    ledBuffer.setRGB(i,r,g,b);
                }else {
                    ledBuffer.setRGB(i,r2,g2,b2);
                }
            }
        }
        led.setData(ledBuffer);
        led.start();
   }


   public void flipColorsBackAndForth(int r1, int g1, int b1,int r2, int g2, int b2, int speed) {
    // build an array of length N that has all of the values of getN(1) to getN(n)
    // build arrays and check if numbers are in arrays
    // check the array in the function scrollN then build an array based of the variable n to send info to an array
        if (X < speed){
            for (var i = 0; i < ledBuffer.getLength(); i++) {
                if (i < ledBuffer.getLength()) {
                    if (i%2==0) {
                        ledBuffer.setRGB(i, r1,g1,b1);
                    } else {
                        ledBuffer.setRGB(i, r2,g2,b2);
                    
                    }
                }
            }
        }else {
            if (X >= speed){
                for (var i = 0; i < ledBuffer.getLength(); i++) {
                    if (i < ledBuffer.getLength()) {
                        if (i%2==0) {
                            ledBuffer.setRGB(i, r2,g2,b2);
                        } else {
                            ledBuffer.setRGB(i, r1,g1,b1);
                        }
                    } 
            
                }
            }
        }
        X = X + 1;

        if(X >= speed*2){
            X = 0;
        }
        led.setData(ledBuffer);
        led.start();
    }
    public void climbBalance( String direction) {
        ledSides = ledBuffer.getLength() * .5;
        if (direction.equals("TooFarLeft")) {  
            for (var i = 0; i < ledBuffer.getLength(); i++) {
                if (i <= ledSides) {
                    ledBuffer.setRGB(i, 255, 0, 0);
                } else {
                    ledBuffer.setRGB(i, 0, 0, 255);
                }    
            }
        } else if (direction.equals("TooFarRight")) {
            for (var i = 0; i < ledBuffer.getLength(); i++) {
                if (i <= ledSides){
                    ledBuffer.setRGB(i, 0, 0, 255);
                } else {
                    ledBuffer.setRGB(i, 255, 0, 0);
                }
            }
        } else {
            for (var i = 0; i < ledBuffer.getLength(); i++) {
                ledBuffer.setRGB(i, 0, 255, 0);
            }
        }
        led.setData(ledBuffer);
        led.start();
   }
    public void climbLeds(int r, int g, int b, int r1, int g1, int b1){
        double percentOn;
        percentOn =  Math.abs(gamepad.getX())* 59;
        // build an array of length N that has all of the values of getN(1) to getN(n)
        // build arrays and check if numbers are in arrays
        // check the array in the function scrollN then build an array based of the variable n to send info to an array
        for (var i = 0; i < ledBuffer.getLength(); i++) {
            // Sets the specified LED to the RGB values for red
            if (i <= percentOn) {
                ledBuffer.setRGB(i, r,g,b);
            } else {
                ledBuffer.setRGB(i, r1,g1,b1);
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
            if (i == ledCounter || i == ledCounter1 || i == ledCounter2 || i == ledCounter3) {
                ledBuffer.setRGB(i, r,g,b);
            } else {
                ledBuffer.setRGB(i,0,0,0);
        }
    }
   
    led.setData(ledBuffer);
    led.start();
   }

}
