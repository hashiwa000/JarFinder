# JarFinder

## Requirement  
 * JDK/JRE 8 (動作確認はOracle JDKを使用)

## Usage
+ jarファイル群から特定の文字列(java/rmi)を含むクラスを探す  

<pre>
>java -jar jarfinder.jar class "c:\Program Files (x86)\NetBeans 8.0.2\ide" Operation
--- c:\Program Files (x86)\NetBeans 8.0.2\ide\modules\com-jcraft-jsch.jar ---
--- c:\Program Files (x86)\NetBeans 8.0.2\ide\modules\com-jcraft-jzlib.jar ---
--- c:\Program Files (x86)\NetBeans 8.0.2\ide\modules\docs\org-netbeans-modules-usersguide.jar ---
--- c:\Program Files (x86)\NetBeans 8.0.2\ide\modules\ext\antlr-runtime-3.4.jar ---
org/antlr/runtime/TokenRewriteStream$RewriteOperation.class

  --- (snip) ---
</pre>

+ jarファイル群から特定の文字列(java/rmi)を含むコンスタントプールエントリーを探す  

<pre>
>java -jar jarfinder.jar constpool "c:\Program Files (x86)\NetBeans 8.0.2\ide" java/rmi
--- c:\Program Files (x86)\NetBeans 8.0.2\ide\modules\com-jcraft-jsch.jar ---
----- com/jcraft/jsch/Buffer.class -----
----- com/jcraft/jsch/Channel$1.class -----
----- com/jcraft/jsch/Channel$MyPipedInputStream.class -----

  --- (snip) ---

----- freemarker/debug/Breakpoint.class -----
----- freemarker/debug/DebugModel.class -----
java/rmi/Remote
java/rmi/RemoteException

  --- (snip) ---
</pre>

+ Usage: jarファイル群を解析して継承関係を表示する  
  (注意：以下の例は、見やすいように関連するクラスを手動で除いています)

<pre>
>java -jar jarfinder.jar extends "c:\Program Files\Java\jdk1.8.0_45";jarfinder.jar

---------- extends ----------
java/lang/Object
	<-java/nio/file/SimpleFileVisitor
		<-jp/hashiwa/jarfinder/JarWalker
	<-jdk/internal/org/objectweb/asm/ClassVisitor
	<-jp/hashiwa/jarfinder/impl/CallTreeClassVisitor
		<-jp/hashiwa/jarfinder/impl/ExtendsClassVisitor
	<-jdk/internal/org/objectweb/asm/MethodVisitor
		<-jp/hashiwa/jarfinder/impl/CallTreeClassVisitor$1
	<-jp/hashiwa/jarfinder/Arguments
	<-jp/hashiwa/jarfinder/JarFileProcessor
	<-jp/hashiwa/jarfinder/JarFinderMain
	<-jp/hashiwa/jarfinder/impl/CallTree
	<-jp/hashiwa/jarfinder/impl/CallTree$CallTreeEntry
 	<-jp/hashiwa/jarfinder/impl/ConstantPoolEntry
 		<-jp/hashiwa/jarfinder/impl/ConstantPoolEntry$Class_info
 		<-jp/hashiwa/jarfinder/impl/ConstantPoolEntry$Double_info
 		<-jp/hashiwa/jarfinder/impl/ConstantPoolEntry$Fieldref_info
 		<-jp/hashiwa/jarfinder/impl/ConstantPoolEntry$Float_info
 		<-jp/hashiwa/jarfinder/impl/ConstantPoolEntry$Integer_info
 		<-jp/hashiwa/jarfinder/impl/ConstantPoolEntry$InterfaceMethodref_info
 		<-jp/hashiwa/jarfinder/impl/ConstantPoolEntry$InvokeDynamic_info
 		<-jp/hashiwa/jarfinder/impl/ConstantPoolEntry$Long_info
 		<-jp/hashiwa/jarfinder/impl/ConstantPoolEntry$MethodHandle_info
 		<-jp/hashiwa/jarfinder/impl/ConstantPoolEntry$MethodType_info
 		<-jp/hashiwa/jarfinder/impl/ConstantPoolEntry$Methodref_info
 		<-jp/hashiwa/jarfinder/impl/ConstantPoolEntry$NameAndType_info
 		<-jp/hashiwa/jarfinder/impl/ConstantPoolEntry$String_info
 		<-jp/hashiwa/jarfinder/impl/ConstantPoolEntry$Utf8_info
 	<-jp/hashiwa/jarfinder/impl/ConstantPoolEntry$1
 	<-jp/hashiwa/jarfinder/impl/ExtendsManager
 	<-jp/hashiwa/jarfinder/impl/JarFileProcessorImpl
 		<-jp/hashiwa/jarfinder/impl/CallTreeProcessor
 		<-jp/hashiwa/jarfinder/impl/ClassNameFilterProcessor
 		<-jp/hashiwa/jarfinder/impl/ConstantPoolFilterProcessor
 		<-jp/hashiwa/jarfinder/impl/ExtendsProcessor
 
-------- implements ---------
jp/hashiwa/jarfinder/JarFileProcessor
	<-jp/hashiwa/jarfinder/impl/JarFileProcessorImpl
</pre>

jarをコマンドライン引数無しで実行すると、(下手な英語で)ヘルプが表示されます。