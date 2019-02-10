package com.b1gg33k.services;

import com.b1gg33k.commands.BotLexical;
import com.b1gg33k.commands.BotLexicon;
import com.b1gg33k.utils.BotSentence;
import com.b1gg33k.utils.CommandNotFoundException;
import org.atteo.classindex.ClassIndex;

import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CommandService {
    private static CommandService instance;
    Map<String,CommandMethods> commands;

    private class CommandMethods {
        Class commandClass;
        List<Method> methods;

        public CommandMethods(Class commandClass) {
            this.commandClass = commandClass;
            this.methods = new ArrayList<>();
        }

        public Class getCommandClass() {
            return commandClass;
        }

        public CommandMethods setCommandClass(Class commandClass) {
            this.commandClass = commandClass;
            return this;
        }

        public List<Method> getMethods() {
            return methods;
        }

        public Method getMethod(Object... params){
            return methods.stream().filter(method -> {
                if (method.getParameterCount() == params.length){
                    for (int paramIndex=0; paramIndex<method.getParameterCount(); paramIndex++){
                        if (!method.getParameterTypes()[paramIndex].isAssignableFrom(params[paramIndex].getClass())){
                            return true;
                        }
                    }
                }
                return false;
            }).findFirst().get();
        }

        public CommandMethods setMethods(List<Method> methods) {
            this.methods = methods;
            return this;
        }

        public CommandMethods addMethod(Method method){
            this.methods.add(method);
            return this;
        }
    }


    public static CommandService getInstance() {
        if (null == instance){
            instance = new CommandService();
        }
        return instance;
    }

    private CommandService() {
        this.commands = new HashMap<>();

        ClassIndex.getAnnotated(BotLexicon.class).forEach(commandClass -> {
            Stream.of(commandClass.getMethods())
                .forEach(method -> Arrays
                    .asList(method.getAnnotation(BotLexical.class))
                    .forEach(lexical -> {
                        if (null != lexical && !this.commands.containsKey(lexical.command())){
                            this.commands.put(lexical.command(),new CommandMethods(commandClass).addMethod(method));
                        };
                    })
            );
        });
    }

    public void explicate(BotSentence botSentence, Object... params) throws CommandNotFoundException {
        if (botSentence.getTokenizer().hasNext()){
            String commandName = botSentence.getTokenizer().nextWord().getContent().toLowerCase();
            try {
                Object[] passParams = Stream.concat(Stream.of(botSentence.getMessage()),Arrays.stream(params)).toArray();
                Method method = findCommandMethod(commandName, passParams);
                method.invoke(method.getDeclaringClass().newInstance(),passParams);
            } catch (Exception e) {
                throw new CommandNotFoundException(e,commandName);
            }
        }
    }

    protected Method findCommandMethod(String commandName, Object... params) throws CommandNotFoundException {
        CommandMethods commandMethod = commands.get(commandName);
        if (null == commandMethod){
            return null;
        }

        List<Class> paramClasses = Arrays
                .asList(params)
                .stream().map(param -> param.getClass())
                .collect(Collectors.toList());

        Method method = commandMethod.getMethod(paramClasses.toArray());
        return method;
    }
}
