#pragma once
#include <iostream>
#include <string>

using namespace std;

class Character {
    private: 

    string name;
    string role;
    int hitPoints;
    int attackBonus;
    int damageBonus;
    int armourClass;

    public: 

    // default constructor
    Character();

    // parameterized constructor for simplified testing
    Character(string, string, int, int, int, int);

    // accessors and mutators
    string getName();
    void setName(string);

    string getRole();
    void setRole(string);

    int getHealth();
    void setHealth(int);

    int getAttackBonus();
    void setAttackBonus(int);

    int getDamageBonus();
    void setDamageBonus(int);

    int getArmourClass();
    void setArmourClass(int);

    // other functions (descriptions in character.cpp)
    // print the character object
    void print(ostream&);

    // attack another character
    void attack(Character&);

    // subtract amount from the character's health
    void damage(int);  

};
