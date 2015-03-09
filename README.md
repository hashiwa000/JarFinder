# JarFinder

Requirement  
* JDK/JRE 8 (動作確認はOracle JDKを使用)

Usage: jarファイル群から特定の文字列(java/rmi)を含むクラスを探す  
    
    java -jar jarfinder.jar class "c:\Program Files (x86)\NetBeans 8.0.2\ide" Operation
    --- c:\Program Files (x86)\NetBeans 8.0.2\ide\modules\com-jcraft-jsch.jar ---
    --- c:\Program Files (x86)\NetBeans 8.0.2\ide\modules\com-jcraft-jzlib.jar ---
    --- c:\Program Files (x86)\NetBeans 8.0.2\ide\modules\docs\org-netbeans-modules-usersguide.jar ---
    --- c:\Program Files (x86)\NetBeans 8.0.2\ide\modules\ext\antlr-runtime-3.4.jar ---
    org/antlr/runtime/TokenRewriteStream$RewriteOperation.class
    
      --- snip ---

Usage: jarファイル群から特定の文字列(java/rmi)を含むコンスタントプールエントリーを探す  
    
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

jarをコマンドライン引数無しで実行すると、(下手な英語で)ヘルプが表示されます。

