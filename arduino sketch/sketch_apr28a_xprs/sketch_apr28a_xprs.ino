#include <SPI.h>
#include <Ethernet.h>

byte mac[] = {0xDE, 0xAD, 0xBE, 0xEF, 0xFE, 0xED};
IPAddress staticIP(192, 168, 1, 22);
EthernetClient client;
char server[] = "192.168.1.65";
char buffer [1024];

void connectToInternet()
{
  // Attempt to connect to Ethernet with DHCP
  if (Ethernet.begin(mac) == 0)
  {
  Serial.print("[ERROR] Failed to Configure Ethernet using DHCP");
  // DHCP failed, attempt to connect to Ethernet with static IP
  Ethernet.begin(mac, staticIP);
  }
  // Delay to let Ethernet shield initialize
  delay(1000);
  // Connection successful
  Serial.println("[INFO] Connection Successful");
  Serial.print("");
  printConnectionInformation();
  Serial.println("-----------------------------------------------");
  Serial.println("");
}

void printConnectionInformation()
{
  // Print Connection Information
  Serial.print("[INFO] IP Address: ");
  Serial.println(Ethernet.localIP());
  Serial.print("[INFO] Subnet Mask: ");
  Serial.println(Ethernet.subnetMask());
  Serial.print("[INFO] Gateway: ");
  Serial.println(Ethernet.gatewayIP());
  Serial.print("[INFO] DNS: ");
  Serial.println(Ethernet.dnsServerIP());
}

void setup(){
  // Initialize serial port
  Serial.begin(9600);
  // Connect Arduino to internet
  connectToInternet();
  checkServerCon();  
}

void checkServerCon(){
  // If you get a connection, report back via serial:
  if (client.connect(server, 3000)) {
    Serial.print("connected to ");
    Serial.println(client.remoteIP());
    // Make a HTTP request:    
    client.println("GET /heroes/ HTTP/1.1");
    client.println("Host: 192.168.1.65");
    client.println("Connection: close");
    client.println();
  } else {
    // If you didn't get a connection to the server:
    Serial.println("connection failed");
  }
}

void printResponse(){
  // If there's incoming data from the net connection.
  // Send it out the serial port. This is for debugging purposes only:
  if (client.available()) {
    char c = client.read();
    Serial.write(c);
  }
}

void postVal(){
  String data = "[\{\"name\": \"Tyrion Lannister\",\"desc\": \"Hand of the Queen\"\}]";  
  // Make a HTTP request
  if(client.connect(server, 3000)){    
    client.print(String("POST /heroes/") + " HTTP/1.1\r\n" +
           "Host: 192.168.1.65" + "\r\n" +
           "Connection: close\r\n"+   
           "Content-Length: " + data.length() + "\r\n" +
           "Content-Type: application/json;charset=UTF-8\r\n\r\n"+
            data +"\r\n");
  }
}

void loop() {
  //postVal(); delay(20000);
  printResponse();
}
