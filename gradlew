����   2| kotlinx/coroutines/DispatchedKt  java/lang/Object  	UNDEFINED $Lkotlinx/coroutines/internal/Symbol; UNDEFINED$annotations ()V executeUnconfined b(Lkotlinx/coroutines/DispatchedContinuation;Ljava/lang/Object;IZLkotlin/jvm/functions/Function0;)Z t(Lkotlinx/coroutines/DispatchedContinuation<*>;Ljava/lang/Object;IZLkotlin/jvm/functions/Function0<Lkotlin/Unit;>;)Z #Lorg/jetbrains/annotations/NotNull; java/lang/Throwable      'kotlinx/coroutines/ThreadLocalEventLoop  INSTANCE )Lkotlinx/coroutines/ThreadLocalEventLoop;  	   $getEventLoop$kotlinx_coroutines_core  ()Lkotlinx/coroutines/EventLoop;  
   kotlinx/coroutines/EventLoop  isUnconfinedQueueEmpty ()Z  
   isUnconfinedLoopActive   
  ! )kotlinx/coroutines/DispatchedContinuation # _state Ljava/lang/Object; % &	 $ ' 
resumeMode I ) *	 $ + !kotlinx/coroutines/DispatchedTask - dispatchUnconfined &(Lkotlinx/coroutines/DispatchedTask;)V / 0
  1 incrementUseCount (Z)V 3 4
  5 kotlin/jvm/functions/Function0 7 invoke ()Ljava/lang/Object; 9 : 8 ; processUnconfinedEvent = 
  >  kotlin/jvm/internal/InlineMarker @ finallyStart (I)V B C
 A D decrementUseCount F 4
  G 
finallyEnd I C
 A J ,handleFatalException$kotlinx_coroutines_core -(Ljava/lang/Throwable;Ljava/lang/Throwable;)V L M
 . N e$iv Ljava/lang/Throwable; $this$runUnconfinedEventLoop$iv #Lkotlinx/coroutines/DispatchedTask; $i$f$runUnconfinedEventLoop 	eventLoop Lkotlinx/coroutines/EventLoop; $this$executeUnconfined +Lkotlinx/coroutines/DispatchedContinuation; 	contState mode doYield Z block  Lkotlin/jvm/functions/Function0; $i$f$executeUnconfined executeUnconfined$default u(Lkotlinx/coroutines/DispatchedContinuation;Ljava/lang/Object;IZLkotlin/jvm/functions/Function0;ILjava/lang/Object;)Z resumeUnconfined )(Lkotlinx/coroutines/DispatchedTask<*>;)V #getDelegate$kotlinx_coroutines_core "()Lkotlin/coroutines/Continuation; d e
 . f resume G(Lkotlinx/coroutines/DispatchedTask;Lkotlin/coroutines/Continuation;I)V h i
  j <$i$a$-runUnconfinedEventLoop-DispatchedKt$resumeUnconfined$1 $this$resumeUnconfined runUnconfinedEventLoop d(Lkotlinx/coroutines/DispatchedTask;Lkotlinx/coroutines/EventLoop;Lkotlin/jvm/functions/Function0;)V v(Lkotlinx/coroutines/DispatchedTask<*>;Lkotlinx/coroutines/EventLoop;Lkotlin/jvm/functions/Function0<Lkotlin/Unit;>;)V e $this$runUnconfinedEventLoop resumeCancellable 5(Lkotlin/coroutines/Continuation;Ljava/lang/Object;)V B<T:Ljava/lang/Object;>(Lkotlin/coroutines/Continuation<-TT;>;TT;)V $this$resumeCancellable v kotlin/jvm/internal/Intrinsics x checkParameterIsNotNull '(Ljava/lang/Object;Ljava/lang/String;)V z {
 y | 
dispatcher (Lkotlinx/coroutines/CoroutineDispatcher; ~ 	 $ � 
getContext &()Lkotlin/coroutines/CoroutineContext; � �
 $ � &kotlinx/coroutines/CoroutineDispatcher � isDispatchNeeded '(Lkotlin/coroutines/CoroutineContext;)Z � �
 � � java/lang/Runnable � dispatch ;(Lkotlin/coroutines/CoroutineContext;Ljava/lang/Runnable;)V � �
 � � kotlinx/coroutines/Job � Key Lkotlinx/coroutines/Job$Key; � �	 � � &kotlin/coroutines/CoroutineContext$Key � "kotlin/coroutines/CoroutineContext � get V(Lkotlin/coroutines/CoroutineContext$Key;)Lkotlin/coroutines/CoroutineContext$Element; � � � � isActive �  � � getCancellationException .()Ljava/util/concurrent/CancellationException; � � � � kotlin/Result � 	Companion Lkotlin/Result$Companion; � �	 � � kotlin/ResultKt � createFailure )(Ljava/lang/Throwable;)Ljava/lang/Object; � �
 � � constructor-impl &(Ljava/lang/Object;)Ljava/lang/Object; � �
 � � kotlin/coroutines/Continuation � 
resumeWith (Ljava/lang/Object;)V � � � � countOrElement � &	 $ � +kotlinx/coroutines/internal/ThreadContextKt � updateThreadContext J(Lkotlin/coroutines/CoroutineContext;Ljava/lang/Object;)Ljava/lang/Object; � �
 � � continuation  Lkotlin/coroutines/Continuation; � �	 $ � kotlin/Unit � Lkotlin/Unit;  �	 � � restoreThreadContext 9(Lkotlin/coroutines/CoroutineContext;Ljava/lang/Object;)V � �
 � � 	job$iv$iv Lkotlinx/coroutines/Job; this_$iv$iv $i$f$resumeCancelled L$i$a$-withCoroutineContext-DispatchedContinuation$resumeUndispatched$1$iv$iv oldValue$iv$iv$iv context$iv$iv$iv $Lkotlin/coroutines/CoroutineContext; countOrElement$iv$iv$iv $i$f$withCoroutineContext value$iv$iv $i$f$resumeUndispatched E$i$a$-executeUnconfined-DispatchedContinuation$resumeCancellable$1$iv 
e$iv$iv$iv %$this$runUnconfinedEventLoop$iv$iv$iv eventLoop$iv$iv $this$executeUnconfined$iv$iv 
mode$iv$iv doYield$iv$iv this_$iv $i$f$resumeCancellable value kotlin/Result$Companion � resumeCancellableWithException 8(Lkotlin/coroutines/Continuation;Ljava/lang/Throwable;)V T<T:Ljava/lang/Object;>(Lkotlin/coroutines/Continuation<-TT;>;Ljava/lang/Throwable;)V $$this$resumeCancellableWithException � 	exception � � � )kotlinx/coroutines/CompletedExceptionally � <init> H(Ljava/lang/Throwable;ZILkotlin/jvm/internal/DefaultConstructorMarker;)V � �
 � � 0kotlinx/coroutines/internal/StackTraceRecoveryKt � recoverStackTrace L(Ljava/lang/Throwable;Lkotlin/coroutines/Continuati