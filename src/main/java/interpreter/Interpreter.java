package interpreter;

import Controller.Controller;
import datastructers.*;
import exceptions.MyException;
import model.expressions.*;
import model.programstate.PrgState;
import model.statements.*;
import model.types.*;
import model.values.*;
import repository.IRepository;
import repository.Repository;
import view.TextMenu;
import view.commands.ExitCommand;
import view.commands.RunExample;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Interpreter {
     public static List<IStmt> getPrgStates() throws MyException {
         IStmt ex1= new CompStmt(new VarDeclStmt("v",new StringType()),
                 new CompStmt(new AssignStmt("v",new ValueExp(new IntValue(2))), new PrintStmt(new
                         VarExp("v"))));
         IStmt ex2 = new CompStmt( new VarDeclStmt("a",new IntType()),
                 new CompStmt(new VarDeclStmt("b",new IntType()),
                         new CompStmt(new AssignStmt("a", new ArithExp(new ValueExp(new IntValue(2)),new
                                 ArithExp(new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5)), 3), 1)),
                                 new CompStmt(new AssignStmt("b",new ArithExp(new VarExp("a"), new ValueExp(new
                                         IntValue(1)), 1)), new PrintStmt(new VarExp("b"))))));
         IStmt ex3 = new CompStmt(new VarDeclStmt("a",new BoolType()),
                 new CompStmt(new VarDeclStmt("v", new IntType()),
                         new CompStmt(new AssignStmt("a", new ValueExp(new BoolValue(true))),
                                 new CompStmt(new IfStmt(new VarExp("a"),new AssignStmt("v",new ValueExp(new
                                         IntValue(2))), new AssignStmt("v", new ValueExp(new IntValue(3)))), new PrintStmt(new
                                         VarExp("v"))))));
         IStmt declaringV = new VarDeclStmt("v", new StringType());
         IStmt assigningV = new AssignStmt("v", new ValueExp(new StringValue("C:\\Users\\doria\\IdeaProjects\\InterpreterWithUI\\src\\main\\in.txt")));
         IStmt openingFile = new OpenRFile(new VarExp("v"));
         IStmt declaringC = new VarDeclStmt("c", new IntType());
         IStmt readingC = new ReadFile(new VarExp("v"), "c");
         IStmt printingC = new PrintStmt(new VarExp("c"));
         IStmt closingFile = new CloseRFile(new VarExp("v"));
         IStmt fin = new CompStmt(declaringV, new CompStmt(assigningV, new CompStmt(openingFile, new CompStmt(declaringC, new CompStmt(readingC,new CompStmt(readingC.deepCopy(), new CompStmt(readingC.deepCopy(), new CompStmt(printingC, closingFile))))))));

         IStmt declaringV2 = new VarDeclStmt("v", new IntType());
         IStmt assigning2 = new AssignStmt("v", new ValueExp(new IntValue(7)));
         IStmt ifStatement = new IfStmt(new RelationalExp(new VarExp("v"), new ValueExp(new IntValue(3)), 0),
                 new PrintStmt(new VarExp("v")),
                 new NopStmt());
         IStmt ex5 = new CompStmt(declaringV2, new CompStmt(assigning2, ifStatement));
         IStmt ex6 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                 new CompStmt(new NewStmt("v", new ValueExp(new IntValue(20))),
                         new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                 new CompStmt(new NewStmt("a", new VarExp("v")),
                                         new CompStmt(new PrintStmt(new VarExp("v")),
                                                 new PrintStmt(new VarExp("a")))
                                 ))
                         )
                 );
         IStmt ex7 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                 new CompStmt(new NewStmt("v", new ValueExp(new IntValue(20))),
                         new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                 new CompStmt(new NewStmt("a", new VarExp("v")),
                                         new CompStmt(new PrintStmt(new HeapReadingExp(new VarExp("v"))),
                                                 new PrintStmt(new ArithExp(new HeapReadingExp(new HeapReadingExp(new VarExp("a"))), new ValueExp(new IntValue(5)), 1)))))));
         IStmt ex8 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                 new CompStmt(new NewStmt("v", new ValueExp(new IntValue(20))),
                         new CompStmt(new PrintStmt(new HeapReadingExp(new VarExp("v"))),
                                 new CompStmt(new HeapWritingStmt("v", new ValueExp(new IntValue(30))),
                                         new CompStmt(new PrintStmt(new ArithExp(
                                                 new HeapReadingExp(new VarExp("v")), new ValueExp(new IntValue(5)), 1)
                                         ), new NopStmt()))
                                 )
                         ));
         IStmt ex9 =  new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                 new CompStmt(new NewStmt("v", new ValueExp(new IntValue(20))),
                         new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                 new CompStmt( new NewStmt("a", new VarExp("v")),
                                         new CompStmt(new NewStmt("v", new ValueExp(new IntValue(30))),
                                                 new CompStmt(new VarDeclStmt("c", new RefType(new IntType())) ,new PrintStmt(new HeapReadingExp(new HeapReadingExp(new VarExp("a"))))))))));
         IStmt ex10 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                 new CompStmt(new NewStmt("v", new ValueExp(new IntValue(20))),
                         new CompStmt(new NewStmt("v", new ValueExp(new IntValue(10))),
                                 new CompStmt(new PrintStmt(new HeapReadingExp(new VarExp("v"))),
                                         new CompStmt(new NewStmt("v", new ValueExp(new IntValue(5))),
                                                 new CompStmt(new VarDeclStmt("a", new RefType(new IntType())),
                                                         new CompStmt(new VarDeclStmt("b", new RefType(new IntType())),
                                                                 new CompStmt(new NewStmt("a", new ValueExp(new IntValue(0))),
                                                                         new CompStmt(new NewStmt("b", new ValueExp(new IntValue(1))),
                                                                                 new NewStmt("v", new ValueExp(new IntValue(3))))))))))));
         IStmt ex11 = new CompStmt(new VarDeclStmt("v", new IntType()),
                 new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(4))),
                         new CompStmt(new WhileStatement(new RelationalExp(new VarExp("v"), new ValueExp(new IntValue(0)), 4), new CompStmt(new PrintStmt(new VarExp("v")), new AssignStmt("v", new ArithExp(new VarExp("v"), new ValueExp(new IntValue(1)), 2)))),
                                 new PrintStmt(new VarExp("v")))));
         IStmt ex12 = new CompStmt(
                 new VarDeclStmt("v", new IntType()),
                 new CompStmt(
                         new AssignStmt("v", new ValueExp(new IntValue(10))),
                         new CompStmt(
                             new VarDeclStmt("a", new RefType(new IntType())),
                             new CompStmt(
                                     new NewStmt("a", new ValueExp(new IntValue(22))),
                                     new CompStmt(
                                             new ForkStmt(
                                                     new CompStmt(
                                                             new HeapWritingStmt("a", new ValueExp(new IntValue(30))),
                                                             new CompStmt(
                                                                     new AssignStmt("v", new ValueExp(new IntValue(32))),
                                                                     new CompStmt(
                                                                             new PrintStmt(new VarExp("v")),
                                                                             new PrintStmt(new HeapReadingExp(new VarExp("a")))

                                                                     )
                                                             )
                                                     )
                                             ),
                                             new CompStmt(
                                                     new NopStmt(),
                                                     new CompStmt(
                                                             new NopStmt(),
                                                             new CompStmt(
                                                                     new NopStmt(),
                                                                     new CompStmt(
                                                                             new NopStmt(),
                                                                             new CompStmt(
                                                                                     new PrintStmt(new VarExp("v")),
                                                                                     new PrintStmt(new HeapReadingExp(new VarExp("a")))
                                                                             )
                                                                     )
                                                             )
                                                     )

                                             )
                                     )
                             )
                         )
                 )
         );

         return new ArrayList<>(Arrays.asList(ex1,ex2,ex3,fin,ex5,ex6,ex7,ex8,ex9,ex10,ex11,ex12));
     }
}
