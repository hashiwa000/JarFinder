# JarFinder

## Requirement  
 * JDK/JRE 8 (����m�F��Oracle JDK���g�p)

## Usage
+ jar�t�@�C���Q�������̕�����(java/rmi)���܂ރN���X��T��  

<pre>
>java -jar jarfinder.jar class "c:\Program Files (x86)\NetBeans 8.0.2\ide" Operation
--- c:\Program Files (x86)\NetBeans 8.0.2\ide\modules\com-jcraft-jsch.jar ---
--- c:\Program Files (x86)\NetBeans 8.0.2\ide\modules\com-jcraft-jzlib.jar ---
--- c:\Program Files (x86)\NetBeans 8.0.2\ide\modules\docs\org-netbeans-modules-usersguide.jar ---
--- c:\Program Files (x86)\NetBeans 8.0.2\ide\modules\ext\antlr-runtime-3.4.jar ---
org/antlr/runtime/TokenRewriteStream$RewriteOperation.class

  --- (snip) ---
</pre>

+ jar�t�@�C���Q�������̕�����(java/rmi)���܂ރR���X�^���g�v�[���G���g���[��T��  

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

+ Usage: jar�t�@�C���Q����͂��Čp���֌W��\������  
  (���ӁF�ȉ��̗�́A���₷���悤�Ɋ֘A����N���X���蓮�ŏ����Ă��܂�)

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

jar���R�}���h���C�����������Ŏ��s����ƁA(����ȉp���)�w���v���\������܂��B