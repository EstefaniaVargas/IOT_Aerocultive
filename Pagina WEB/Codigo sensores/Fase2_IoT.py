#      FASE 2: IoT

import smbus                         #Librería para el manejo de I2C
import time                          #Librería para funciones de sleep
import Adafruit_DHT                  #Librería para el manejo del DHT22
import paho.mqtt.client as mqtt      #Conexion  por medio de MQTT
import paho.mqtt.publish as publish  #Conexion  por medio de MQTT

#Variables para mqtt
canalid = "1336320"                                  #ID del canal de thingspeak
apikey = "LOZ453HTZ11I7JVJ"                          #Llave para conectar on el canal
servidor = "mqtt.thingspeak.com"                     #Servidor donde se encuentra alojada la base de datos
transport_mqtt="tcp"                                 #Protocolo capa de transporte
port_mqtt=1883                                       #Puerto de comunicacion de MQTT
topic = "channels/" + canalid + "/publish/" + apikey #Link de acceso


Huedad = 0   
Temperatura= 0

bus = smbus.SMBus(1)#Canal I2C

#DEFINICIÓN DE REGISTROS DEL ADC: ADS1115

ADDRESS_ADC= 0x48 # Direccion del esclavo 1001000
#Registros del ADS1115 
ADS1115_REG_POINTER_CONVERT=0x00   #Resultado de la última conversión
ADS1115_REG_POINTER_CONFIG=0x01    #Registro de configuración
ADS1115_REG_POINTER_LOWTHRESH=0x02 #Parte baja del Threshold
ADS1115_REG_POINTER_HIGHTHRESH=0x03#Parte alta del Threshold
#Configuración del ADC
ADS_REG_CONF_SINGLE_BEGIN=0X80     #Empezar una conversión
ADS_REG_CONF_SINGLE_A0=0x40        #Para SINGLE CONVERSION en la entrada A0
ADS_REG_CONF_PGA_6144=0x00         #Para rango +/-6.144 range
ADS_REG_CONF_SINGLE_MODE=0x01      #Se pone en modo sleep después de una conversión
ADS_REG_CONF_DR_860SPS=0xE0        #860 muestras por segundos
ADS_REG_CONF_CMODE_TRAD=0x00       #Comparador tradicional para el ADC
ADS_REG_CONF_CQUE_NONE= 0x03       #Valor por default
#Inicialización del ADC
CONFIG_REG=[ADS_REG_CONF_SINGLE_BEGIN|ADS_REG_CONF_SINGLE_A0|ADS_REG_CONF_PGA_6144|ADS_REG_CONF_SINGLE_MODE,ADS_REG_CONF_DR_860SPS|ADS_REG_CONF_CQUE_NONE]
bus.write_i2c_block_data(ADDRESS_ADC,ADS1115_REG_POINTER_CONFIG,CONFIG_REG)

#DEFINICIÓN DE REGISTROS DEL SENSOR DE INTENSIDAD LUMÍNICA: BH1750

ADDRESS_LUZ = 0x23 # Direccion del esclavo 00100011

#Registros del BH1750 
REG_POWER_DOWN = 0x00              #Sensor desactivado
REG_POWER_ON   = 0x01              #Sensor esperando para medir
REG_RESET      = 0x07              #Reset
ONE_TIME_HIGH_RES_MODE_1 = 0x20    #El sensor convierte una vez con 1lx de resolución--Se pone en power down cuando termina

#DEFINICIÓN DE REGISTROS DEL SENSOR DE HUMEDAD Y TEMPERATURA
DHT_SENSOR = Adafruit_DHT.DHT22    #Inicialización del DHT22 
DHT_PIN = 4                        #Leer el sensor en el pin GPIO4

def leerIntensidad():             #Función para leer la intensidad lumnica en I2C
    data = bus.read_i2c_block_data(ADDRESS_LUZ,ONE_TIME_HIGH_RES_MODE_1)#Se lee el sensor por I2C en el modo 1 de alta resolución
    intensidad_luminica=(data[1] + (256 * data[0])) / 1.2
    return (intensidad_luminica)

def leerADC():                    #Función para leer el dato de pH a través del ADC ADS1115
  data = bus.read_i2c_block_data(ADDRESS_ADC,ADS1115_REG_POINTER_CONVERT,2)
  analogVal=data[0]*256+data[1]
  voltaje=(analogVal*12.288/65535)/1.15
  pH=(voltaje-2.5)/0.18
  return (pH)

def leerDHT():                    #Función para leer la temperatura y la humedad del DHT22
    global Humedad
    global Temperatura
    Humedad,Temperatura=Adafruit_DHT.read_retry(DHT_SENSOR, DHT_PIN) #Lectura del DHT22
    return
    
while True:
    leerDHT()
    intensidad_luminica=leerIntensidad()
    valorADC=leerADC()
    pH=leerADC()
    Tiempo= time.ctime(time.time())
    
    print("Temperatura={0:0.1f}°C  Humedad={1:0.1f}%". format(Temperatura, Humedad))
    print("Intensidad Luminica : " + format(intensidad_luminica,'.2f') + " lx")
    pri…