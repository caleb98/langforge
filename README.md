# Langforge

This project is both a Java library and JavaFX UI application meant to provide tools that assist in the creation of constructed languages. The library code can be found in `/langforge-core`, while the JavaFX UI application code is located in the `/langforge-app*` folders.

## Building and Running

You can build the project by cloning the repository and running the `./gradlew build` command from the root project directory. This will build both the core library code and the UI application. To run or debug the UI app, use `./gradlew run` or `./gradlew runDebug`, respectively.

## Features

Development thus far has focused on constructed language phonology. Main features include:

* Ability to create and manipulate phonemes using realistic features from human language, or using custom features for languages used by other species.
* Builtin collections containing definitions of standard phonemes that are part of the International Phonetic Alphabet, along with mappings for the corresponding IPA characters.
* `PhonemeString` provides a `String`-like class for dealing with sequences of phonemes and performing operations on them, as well as annotating phonemes with contextual data (such as it's position in a syllable or word).
* `PhonologicalRule`, which allows defining [phonological rules](https://en.wikipedia.org/wiki/Phonological_rule) dictating how phonemes may change in certain spoken contexts.
* `PhonologicalRuleApplicator`, which allows applying a `PhonologicalRule` to a `PhonemeString` to produce the result of that rule's transformation in the context of the phoneme string.
* Support for defining valid syllable structures and validating `PhonemeString`s against them.
* Support for annotating phonemes of a `PhonemeString` with correct contextual data based on supplied syllable structures and phonological rules.

Further development will focus on the refinement of these features, as well as expanding into the areas of morphology and syntax.
