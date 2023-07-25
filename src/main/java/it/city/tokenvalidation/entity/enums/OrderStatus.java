package it.city.tokenvalidation.entity.enums;

public enum OrderStatus {
    DRAFT,//QORALAMA
    NEW,//ZAKAZ KELDI LEKIN HALI NAVBATI KELMADI
    IN_PROGRESS,//ZAKAZ ISHLANYABDI
    COMPLETED,//ZAKAZ BAJARILDI LEKIN FEEDBACK BERILMADI
    CLOSED,//ZAKAZ TUGATILDI
    REJECTED,//ZAKAZ ROLE_ADMIN TOMONIDAN RAD ETILDI
    CANCELLED//ZAKAZ CLIENT TOMONIDAN RAD ETILDI
}
