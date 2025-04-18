//Standard PWM DC control
int E1 = 5;  //M1 Speed Control
int E2 = 6;  //M2 Speed Control
int M1 = 4;  //M1 Direction Control
int M2 = 7;  //M2 Direction Control
int duration = 0;

void stop(void)  //Stop
{
  digitalWrite(E1, LOW);
  digitalWrite(E2, LOW);
}
void advance(char a, char b)  //Move forward
{
  analogWrite(E1, a);  //PWM Speed Control
  digitalWrite(M1, HIGH);
  analogWrite(E2, b);
  digitalWrite(M2, HIGH);
}
void back_off(char a, char b)  //Move backward
{
  analogWrite(E1, a);
  digitalWrite(M1, LOW);
  analogWrite(E2, b);
  digitalWrite(M2, LOW);
}
void turn_L(char a, char b)  //Turn Left
{
  analogWrite(E1, a);
  digitalWrite(M1, LOW);
  analogWrite(E2, b);
  digitalWrite(M2, HIGH);
}
void turn_R(char a, char b)  //Turn Right
{
  analogWrite(E1, a);
  digitalWrite(M1, HIGH);
  analogWrite(E2, b);
  digitalWrite(M2, LOW);
}

void setup(void) {
  int i;
  for (i = 4; i <= 7; i++)
    pinMode(i, OUTPUT);
  Serial.begin(115200);  //Set Baud Rate
  Serial.println("CONNNECTED");
  Serial.println("z = Instructions");

}
void loop(void) {
  
  if (Serial.available()) {
    char val = Serial.read();

    if (val != -1) {
      switch (val) {
        case 'w':      //Move Forward duration
          advance(255, 255);  
          delay (duration);
          stop();
          break;
        case 'W';     //Move Forward
          advance(255, 255); 
          break;
        case 's':    //Move Backward duration
          back_off(255, 255);  
          delay (duration);
          stop();
          break;
        case 'S':    //Move Backward
          back_off(255, 255); 
          break;
        case 'a':    //Turn Left duration
          turn_L(100, 100); 
          delay (duration);
          stop();
          break;
        case 'A':   //Turn Left 
          turn_L(100, 100);
          break;
        case 'd': //Turn Right duration        
          turn_R(100, 100);  
          delay (duration);
          stop();
          break;
        case 'D':  //Turn Right
          turn_R(100, 100);
          break;
        case 'z'|'Z':
          Serial.println("w = M1 high, d = M1 Low, a = m2 low, s = m2 high, x = stop, z = Instructions"); //Display instructions in the serial monitor
          break;
        case 'x':
          stop();
          break;
      }
    } else stop();
  }
}
