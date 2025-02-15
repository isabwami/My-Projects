#include "character.h"
#include <iomanip>
#include <random>
#include <cstdlib>

// default constructor
Character::Character() {
    // initialize all fields to default values
    name = "invalid";
    role = "invalid";
    hitPoints = 0;
    attackBonus = 0;
    damageBonus = 0;
    armourClass = 0;
}

// parameterized constructor
Character::Character(string newName, string newRole, int newHitPoints, int newAttackBonus, int newDamageBonus, int newArmourClass) {
    // initialize all fields to function parameters
    name = newName;
    role = newRole;
    hitPoints = newHitPoints;
    attackBonus = newAttackBonus;
    damageBonus = newDamageBonus;
    armourClass = newArmourClass;
}

// accessors and mutators
string Character::getName() {
    return name;
}

void Character::setName(string newName) {
    name = newName;
}

string Character::getRole() {
    return role;
}

void Character::setRole(string newRole) {
    role = newRole;
}

int Character::getHealth() {
    return hitPoints;
}

void Character::setHealth(int health) {
    hitPoints = health;
}

int Character::getAttackBonus() {
    return attackBonus;
}

void Character::setAttackBonus(int newAttackBonus) {
    attackBonus = newAttackBonus;
}

int Character::getDamageBonus() {
    return damageBonus;
}

void Character::setDamageBonus(int newDamageBonus) {
    damageBonus = newDamageBonus;
}

int Character::getArmourClass() {
    return armourClass;
}

void Character::setArmourClass(int newArmourClass) {
    armourClass = newArmourClass;
}

// other functions
/*
print: prints the character object in the character summary format
    Returns: void
    Parameters: 
        os (ostream ref) - reference to output stream the character will be printed to
*/
void Character::print(ostream& os) {
    os << name << " the " << role << endl;
    os << "HP: " << hitPoints << endl;
    os << "AB: " << attackBonus << endl;
    os << "DB: " << damageBonus << endl;
    os << "AC: " << armourClass << endl;
}

/*
attack: attack another character
    Returns: void
    Parameters: 
        otherCharacter (Character reference) - reference to character object that is being attacked
*/
void Character::attack(Character& otherCharacter) {
    
    cout << name << " attacks!" << endl; // print attack message

    int attackPoints; // declare attack points which will be initialized to the dice roll + the character's attack bonus
    int damagePoints; // declare damage points which will be initialized to the dice roll + the character's damage bonus

    attackPoints = (rand() % 20) + 1; // roll a number between 1 and 20
    attackPoints += attackBonus; // add the attack bonus
    cout << "Attack roll: " << attackPoints - attackBonus << " + " << attackBonus << " = " << attackPoints << " --> "; // print the total attack points

    // determine if the attack hits or misses 
    if (attackPoints >= otherCharacter.armourClass) {
        cout << "HIT!" << endl; // if it hits, print HIT!
        damagePoints = (rand() % 10) + 1; // roll a number between 1 and 10
        damagePoints += damageBonus; // add the damage bonus

        cout << "Damage: " << damagePoints - damageBonus << " + " << damageBonus << " = " << damagePoints << endl; // print the total damage points

        otherCharacter.damage(damagePoints); // subtract damage points from the other character's health
    } else {
        cout << "MISS!" << endl << endl; // if it misses, print MISS!
    }
}

/*
damage: subtracts the damage amount from the character depending on the result of the attack() function
    Returns: void
    Parameters: 
        amount (int) - amount of damage dealt to this character by the oponent 
*/
void Character::damage(int amount) {
    hitPoints -= amount; // subtract the damage amount from character's health
    
    // determine if character has lost
    if (hitPoints > 0) {
        cout << name << " has " << hitPoints << " hit points remaining" << endl << endl; // if character's health is greater than 0, print remaining health and let the game continue 
    } else {
        cout << name << " has " << 0 << " hit points remaining" << endl << endl; // if character's health is 0 or less, print final health update before game end
    }
}
