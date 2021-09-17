# Code Smells & Refatoração: Payroll, Projeto de Software.

## Code Smells:
### Long Method:
"A method contains too many lines of code. Generally, any method longer than ten lines should make you start asking questions." - [refactoring.guru](http://https://refactoring.guru/smells/long-method "refactoring.guru")
- 'makePayment()': [veja aqui.](https://github.com/souzag/p2_refactor/commit/cd96f6ee3bc758d86ba6318ad6a4ebbb8689aeab "veja aqui.")
- 'toString()': [veja aqui.](https://github.com/souzag/p2_refactor/commit/4891b58d465535e6936893a27814fbf6b00e284d "veja aqui.")

### Speculative Generality:
"There's an unused class, method, field or parameter." - [refactoring.guru](https://refactoring.guru/smells/speculative-generality "refactoring.guru")
- Construtores e 'set()': [veja aqui.](https://github.com/souzag/p2_refactor/commit/0a2edb3b64e176a127df34f3ccbdd383c86442e9 "veja aqui.")

### Feature Envy:
"A method accesses the data of another object more than its own data." - [refactoring.guru](https://refactoring.guru/smells/feature-envy "refactoring.guru")
- 'VerifyPayDate()' em 'PaymentController' ao invés de 'PaymentData': [veja aqui.](https://github.com/souzag/p2_refactor/commit/38b8ced6a6e8b7d18a77cf8f9b247893cf378fd7 "veja aqui.")

## Design Patterns e Refatoração:
### Strategy: 
"Strategy is a behavioral design pattern that lets you define a family of algorithms, put each of them into a separate class, and make their objects interchangeable." - [refactoring.guru](https://refactoring.guru/design-patterns/strategy "refactoring.guru")
- Necessário para resolver o *code smell* de *feature envy* citado acima; além disso, através do strategy, foi possível modularizar a agenda de pagamentos: [veja aqui.](https://github.com/souzag/p2_refactor/commit/38b8ced6a6e8b7d18a77cf8f9b247893cf378fd7 "veja aqui.")
