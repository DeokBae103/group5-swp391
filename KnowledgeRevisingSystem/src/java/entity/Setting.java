/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

/**
 *
 * @author Duc Anh
 */
public class Setting {
   private int setting_id; 
   private String name;
   private String type;
   private String description;
   private boolean status;
   private int order;

    public Setting() {
    }

    public Setting(int setting_id,String name,String type,  String description,boolean status,int order) {
        this.setting_id = setting_id;
        this.type = type;
        this.name = name;
        this.status = status;
        this.description = description;
        this.order = order;
    }

    public int getSetting_id() {
        return setting_id;
    }

    public void setSetting_id(int setting_id) {
        this.setting_id = setting_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
    
    

    @Override
    public String toString() {
        return setting_id +" "+ type +" "+ name +" "+ description +" "+ status;
    }
   
   
}
