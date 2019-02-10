package com.b1gg33k.commands;

import sx.blah.discord.handle.obj.IMessage;

import javax.annotation.processing.*;
import javax.lang.model.element.*;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;
import java.util.Set;

@SupportedAnnotationTypes({
        "com.b1gg33k.commands.BotLexicalHelp",
        "com.b1gg33k.commands.BotLexical"
})
public class BotAnnotationProcessor extends AbstractProcessor {
    private Types typeUtils;
    private Elements elementUtils;
    private Filer filer;
    private Messager messager;

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        roundEnv.getElementsAnnotatedWith(BotLexicalHelp.class).forEach(element -> validateLexicals(element,BotLexicalHelp.class));
        roundEnv.getElementsAnnotatedWith(BotLexical.class).forEach(element -> validateLexicals(element,BotLexical.class));

        return true;
    }

    private void validateLexicals(Element element, Class klass){
        if (element.getKind() != ElementKind.METHOD){
            this.messager.printMessage(Diagnostic.Kind.ERROR,"Annotation " + klass.getSimpleName() + " can only be applied to methods");
        } else {
            //We know this annotation applies to a method.
            ExecutableElement executableElement = (ExecutableElement) element;
            if (!executableElement.getModifiers().contains(Modifier.PUBLIC)){
                this.messager.printMessage(Diagnostic.Kind.ERROR,"Method annotated with " + klass.getSimpleName() + " must be public.");
            }
            if (!executableElement.getTypeParameters().contains(IMessage.class)){
                this.messager.printMessage(Diagnostic.Kind.ERROR,"Method annotated with " + klass.getSimpleName() + " must accept an " + IMessage.class.getName() + " parameter");
            }
        }
    }

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        this.typeUtils = processingEnv.getTypeUtils();
        this.elementUtils = processingEnv.getElementUtils();
        this.filer = processingEnv.getFiler();
        this.messager = processingEnv.getMessager();
        super.init(processingEnv);
    }
}
