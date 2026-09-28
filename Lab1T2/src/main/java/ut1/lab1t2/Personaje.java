package ut1.lab1t2;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Personaje {
    private String rango,nombre;
    
    public Personaje(){
    }
    
    public Personaje(String nombre, String rango){
        this.nombre = nombre;
        this.rango = rango;
    }
    @XmlElement
    public String getRango() {
        return rango;
    }
    public void setRango(String rango) {
        this.rango = rango;
    }
    @XmlElement
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
