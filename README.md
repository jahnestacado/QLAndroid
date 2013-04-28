#QLAndroid: Android version of QL, a Domain Specific Language for Questionnaires


A DSL for auto-generated interactive questionnaire sheets. Questionnaires are characterized by conditional entry fields
and (spreadsheet-like) dependency-directed computation. It reads from a text file the content(source code), conducting
lexical and syntactic analysis, and auto-generates interactive Android UI Views. The application allows the user to 
export in PDF format the results of the filled form and view them via the default PDF Viewer app of the smartphone 
device. ANTLR java parser and the java programming languages is used for the implementation of this project.


##Example

The following example presents a possible textual representation of a simple questionnaire.
 ```
form Box1HouseOwning {
    hasSoldHouse: “Did you sell a house in 2010?” boolean
    hasBoughtHouse: “Did you by a house in 2010?” boolean
    hasMaintLoan: “Did you enter a loan for maintenance/reconstruction?” boolean
      if (hasSoldHouse) {
          sellingPrice: “Price the house was sold for:” money
          privateDebt: “Private debts for the sold house:” money
          valueResidue: “Value residue:” money(sellingPrice - privateDebt)
      }
}

 ```
 

This simple form should generate into a GUI.

##Features:

####1.QL IDE 
* QL Editor
* QL Console
* Utility buttons (run code, clear code and load sample code)

####2.Auto-generated interactive questionnaire form

####3.Export Utilities
* PDF format


##Syntax

QL consists of questions grouped in a top-level form construct. First, each question identified by a name that at the 
same time represents the result of the question. In other words the name of a question is also the variable that holds 
the answer. Second, a question has a label that contains the actual question text presented to the user. (Note that
technically this is a presentation issue that could be in a separate language for layout and styling, but to make QL
standalone we need it here. See below for more on the layout language.) Third, every question has a type. Finally, a 
question can optionally be associated to an expression: this makes the question computed. A questionnaire consists of a
number of questions arranged in sequential and conditional structures, and grouping constructs. Sequential composition 
prescribes the order of presentation. Conditional structures associate an enabling condition to a question, in which
case the question should only be presented to the user if and when the condition becomes true. The expression language 
used in conditions is the same as the expressions used in computed questions. Grouping does not have any semantics except 
to associate a single condition to multiple questions at once. For expressions we restrict ourselves to booleans 
(e.g., && , || and ! ), comparisons ( < ,> , >= , <= , != and == ) and basic arithmetic ( + , - , * and / ). 
The supported types are:boolean, string, integer,and money/currency.
