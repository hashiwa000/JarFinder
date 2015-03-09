# JarFinder

Requirement  
* JDK/JRE 8 (����m�F��Oracle JDK���g�p)

Usage: jar�t�@�C���Q�������̕�����(java/rmi)���܂ރN���X��T��  
    
    java -jar jarfinder.jar class "c:\Program Files (x86)\NetBeans 8.0.2\ide" Operation
    --- c:\Program Files (x86)\NetBeans 8.0.2\ide\modules\com-jcraft-jsch.jar ---
    --- c:\Program Files (x86)\NetBeans 8.0.2\ide\modules\com-jcraft-jzlib.jar ---
    --- c:\Program Files (x86)\NetBeans 8.0.2\ide\modules\docs\org-netbeans-modules-usersguide.jar ---
    --- c:\Program Files (x86)\NetBeans 8.0.2\ide\modules\ext\antlr-runtime-3.4.jar ---
    org/antlr/runtime/TokenRewriteStream$RewriteOperation.class
    
      --- snip ---

Usage: jar�t�@�C���Q�������̕�����(java/rmi)���܂ރR���X�^���g�v�[���G���g���[��T��  
    
    java -jar jarfinder.jar constpool "c:\Program Files (x86)\NetBeans 8.0.2\ide" java/rmi
    --- c:\Program Files (x86)\NetBeans 8.0.2\ide\modules\com-jcraft-jsch.jar ---
    ----- com/jcraft/jsch/Buffer.class -----
    ----- com/jcraft/jsch/Channel$1.class -----
    ----- com/jcraft/jsch/Channel$MyPipedInputStream.class -----
    
      --- snip ---
    
    ----- freemarker/debug/Breakpoint.class -----
    ----- freemarker/debug/DebugModel.class -----
    java/rmi/Remote
    java/rmi/RemoteException
    
      --- snip ---

jar���R�}���h���C�����������Ŏ��s����ƁA(����ȉp���)�w���v���\������܂��B

