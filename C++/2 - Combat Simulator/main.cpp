#include <iostream>
#include "character.h"

using namespace std;

void setInfo(Character &, string);

/*
main: controls the primary gameplay of the program
    Returns: 0 upon the successful completion of the program
    Parameters: N/A
*/
int main() {
    
    srand(time(NULL)); // seed the random number generator

    string name; // declare string to store character name to be used in setInfo()

    Character character1; // declare first character object
    Character character2; // declare second character object
    
    cout << "First character name? " << endl; // prompt user for the first character's name
    cin >> name; // store the result in the name variable

    setInfo(character1, name); // call the setInfo() function on character1 with the character's given name
    cout << endl; // print new line

    cout << "Second character name? " << endl; // prompt user for the second character's name
    cin >> name; // store the result in the name variable 

    setInfo(character2, name); // call the setInfo() function on character2 with the character's given name
    cout << endl << "Simulated Combat:" << endl; // begin simulated combat

   //for easy testing of simulated combat, comment out lines 17-32, and uncomment lines 35-36 for pre-generated characters
   //Character character1("Uglar", "Barbarian", 80, 5, 5, 24);
   //Character character2("Zimzziz", "Wizard", 40, 5, 15, 18);

    while (true) {
        character1.attack(character2); // call attack from character1 on character2
        if (character2.getHealth() <= 0) { // check if character2 has lost after the attack
            cout << character1.getName() << " Wins! " << endl; // if so, print the character1 win message
            break; // break out of the loop
        }
        character2.attack(character1); // call attack from character2 on character1
        if (character1.getHealth() <= 0) { // check if character1 has lost after the attack
            cout << character2.getName() << " Wins! " << endl; // if so, print the character2 win message
            break; // break out of the loop
        }
    }

    return 0;
}

/*
setInfo: prompts user for character info and sets each field of character object to values specified by the user
    Returns: void
    Parameters: 
        character (Character ref) - reference to the character object being configured
        name (string) - name of the character 
*/
void setInfo(Character & character, string name) {

    // character info variables
    string role;
    int hitPoints;
    int attackBonus;
    int damageBonus;
    int armourClass;

    character.setName(name); // set the character's name to the given name
    cout << endl; // print new line

    cout << name << "'s role? " << endl; // prompt user for character's role
    cin >> role; // store the input in the role variable
    character.setRole(role); // set the the character's role to the given role
    cout << endl; // print new line

    cout << name << " the " << role << "'s hit points? " << endl; // prompt user for character's hit point value (health)
    cin >> hitPoints; // store the input in the hitPoints varaiable
    character.setHealth(hitPoints); // set the character's hitPoints value to the given value
    cout << endl; // print new line

    cout << name << " the " << role << "'s attack bonus? " << endl; // prompt user for character's attack bonus value
    cin >> attackBonus; // store the input in the attackBonus variable
    character.setAttackBonus(attackBonus); // set the character's attackBonus value to the given value
    cout << endl; // print new line

    cout << name << " the " << role << "'s damage bonus? " << endl; // prompt user for character's damage bonus value
    cin >> damageBonus; // store the input in the damageBonus variable
    character.setDamageBonus(damageBonus); // set the character's damageBons value to the given value
    cout << endl; // print new line

    cout << name << " the " << role << "'s armour class? " << endl; // prompt user for character's armour class value
    cin >> armourClass; // store input in the armourClass variable
    character.setArmourClass(armourClass); // set the character's armourClass value to the given value
    cout << endl; // print new line

    // print character summary
    cout << "Character Summary" << endl; 
    cout << "-----------------" << endl;

    character.print(cout); // call the character's print method with ostream cout
}
