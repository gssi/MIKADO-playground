# MIKADO Playground

 MIKADO is a project created by Maria Teresa Rossi for her PhD thesis under the supervision of Ludovico Iovino and Manuel Wimmer,  with the collaboration of Martina De Sanctis. 
                "MIKADO - A Smart City KPIs Assessment Modeling Framework" is a framework that enables a qualitative assessment of Smart Cities based on Key Performance Indicators (KPIs). The assessment is carried out in terms of smartness and sustainability to support the inclusive growth of smart cities with respect to the European Sustainable Development Goals (SDGs). MIKADO uses Model-Driven Engineering techniques to model cities and define KPIs using standard and processable languages.
                <a href="https://modeling-languages.com/mikado-smart-city-kpis-assessment-modeling/">More info</a>.<br>
                This live environment is based and inspired to the <a href="https://www.eclipse.org/epsilon/live/">Epsilon Playground</a>.





## Why MIKADO for evaluating Smart Cities?

- **One syntax to rule them all:** All languages in Epsilon build on top of a [common expression language](doc/eol) which means that you can reuse code across your model-to-model transformations, code generators, validation constraints etc.
- **Integrated development tools:**  All languages in Epsilon are supported by editors providing syntax and error highlighting, code templates, and graphical tools for configuring, running, debugging and profiling Epsilon programs. 
- **Documentation, Documentation, Documentation:** More than [60 articles](doc/articles), [15 screencasts](doc/screencasts) and [40 examples](doc/examples) are available to help you get from novice to expert.
- **Strong support for EMF:** Epsilon supports all flavours of EMF, including reflective, generated and non-XMI (textual) models such as these specified using Xtext or EMFText-based DSLs.
- **No EMF? No problem:** While Epsilon provides strong support for EMF, it is not bound to EMF at all. In fact, support for EMF is implemented as a driver for the model connectivity layer of Epsilon. Other drivers provide support for XML, CSV, Simulink and you can even roll out your own driver!
- **No Eclipse? No problem either:** While Epsilon provides strong support for Eclipse, we also provide [standalone JARs through Maven Central](download/#maven) that you can use to [embed Epsilon in your plain Java](doc/articles/run-epsilon-from-java) or Android application.
- **Mix and match:** Epsilon poses no constraints on the number/type of models you can use in the same program. For example, you can write a transformation that transforms an XML-based and an EMF-based model into a Simulink model and also modifies the source EMF model.
- **Plumbing included:** You can use the [ANT Epsilon tasks](doc/workflow) to compose Epsilon programs into complex workflows. Programs executed in the same workflow can share models and even pass parameters to each other.
- **Extensible:** Almost every aspect of Epsilon is extensible. You can add support for your [own type of models](doc/articles/developing-a-new-emc-driver), extend the Eclipse-based development tools, add a new method to the String type, or even implement [your own model management language](doc/articles/developing-a-new-language) on top of EOL.
- **Java is your friend:** You can call methods of Java classes from all Epsilon programs to reuse code you have already written or to perform tasks that Epsilon languages do not support natively.
- **Parallel execution:** Since 2.0, Epsilon is multi-threaded, which includes first-order operations and some of the rule-based languages, making it faster than other interpreted tools.
- **All questions answered:** The [Epsilon forum](forum) contains more than 9,500 posts and we're proud that no question has ever gone unanswered.
- **We're working on it:** Epsilon has been an Eclipse project since 2006 and it's not going away any time soon.

## License

MIKADO is licensed under the [Eclipse Public License 2.0](https://www.eclipse.org/legal/epl-2.0/).

## Credits

This project is based on Epsilon live backend and it has been inspired by the <a href="https://www.eclipse.org/epsilon/live/">Epsilon Playground</a> website. We also thank Dimitris Kolovos for the kind support.