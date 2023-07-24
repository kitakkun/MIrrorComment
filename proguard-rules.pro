-keep class org.apache.hc.** { *; }
-keep class io.github.bonigarcia.wdm.** { *; }
-keep class org.openqa.selenium.** { *; }
-keep class io.netty.** { *; }
-keep class com.github.kitakkun.ktvox.** { *; }
-keepclassmembers class com.github.kitakkun.ktvox.schema.** {
    <init>(...);
}
-keepattributes Signature
-keepattributes *Annotation*
-keep class com.github.kitakkun.ktvox.schema.** { *; }
-keepclassmembers class com.github.kitakkun.ktvox.schema.** { *; }
-keep class sun.misc.Unsafe { *; }
-dontwarn sun.misc.Unsafe

-dontoptimize

-dontwarn android.net.ssl.SSLSockets
-dontwarn android.os.Build
-dontwarn android.os.Build$VERSION
-dontwarn android.os.Handler
-dontwarn android.os.Looper
-dontwarn android.util.Log
-dontwarn com.aayushatharva.brotli4j.Brotli4jLoader
-dontwarn com.aayushatharva.brotli4j.decoder.DecoderJNI
-dontwarn com.aayushatharva.brotli4j.decoder.DecoderJNI$Status
-dontwarn com.aayushatharva.brotli4j.decoder.DecoderJNI$Wrapper
-dontwarn com.aayushatharva.brotli4j.encoder.BrotliEncoderChannel
-dontwarn com.aayushatharva.brotli4j.encoder.Encoder
-dontwarn com.aayushatharva.brotli4j.encoder.Encoder$Mode
-dontwarn com.aayushatharva.brotli4j.encoder.Encoder$Parameters
-dontwarn com.github.dockerjava.jaxrs.JerseyDockerCmdExecFactory
-dontwarn com.github.dockerjava.jaxrs.JerseyDockerHttpClient
-dontwarn com.github.dockerjava.jaxrs.JerseyDockerHttpClient$Builder
-dontwarn com.github.luben.zstd.BufferPool
-dontwarn com.github.luben.zstd.Zstd
-dontwarn com.github.luben.zstd.ZstdInputStream
-dontwarn com.github.luben.zstd.ZstdOutputStream
-dontwarn com.google.auto.value.AutoValue
-dontwarn com.google.auto.value.AutoValue$CopyAnnotations
-dontwarn com.google.auto.value.extension.memoized.Memoized
-dontwarn com.google.protobuf.ExtensionRegistry
-dontwarn com.google.protobuf.ExtensionRegistryLite
-dontwarn com.google.protobuf.MessageLite
-dontwarn com.google.protobuf.MessageLite$Builder
-dontwarn com.google.protobuf.MessageLiteOrBuilder
-dontwarn com.google.protobuf.Parser
-dontwarn com.google.protobuf.nano.CodedOutputByteBufferNano
-dontwarn com.google.protobuf.nano.MessageNano
-dontwarn com.jcraft.jzlib.Deflater
-dontwarn com.jcraft.jzlib.Inflater
-dontwarn com.jcraft.jzlib.JZlib
-dontwarn com.jcraft.jzlib.JZlib$WrapperType
-dontwarn com.ning.compress.BufferRecycler
-dontwarn com.ning.compress.lzf.ChunkDecoder
-dontwarn com.ning.compress.lzf.ChunkEncoder
-dontwarn com.ning.compress.lzf.LZFChunk
-dontwarn com.ning.compress.lzf.LZFEncoder
-dontwarn com.ning.compress.lzf.util.ChunkDecoderFactory
-dontwarn com.ning.compress.lzf.util.ChunkEncoderFactory
-dontwarn com.oracle.svm.core.annotate.Alias
-dontwarn com.oracle.svm.core.annotate.InjectAccessors
-dontwarn com.oracle.svm.core.annotate.RecomputeFieldValue
-dontwarn com.oracle.svm.core.annotate.RecomputeFieldValue$Kind
-dontwarn com.oracle.svm.core.annotate.TargetClass
-dontwarn com.squareup.javapoet.AnnotationSpec
-dontwarn com.squareup.javapoet.AnnotationSpec$Builder
-dontwarn com.squareup.javapoet.ClassName
-dontwarn edu.umd.cs.findbugs.annotations.SuppressFBWarnings
-dontwarn io.javalin.Javalin
-dontwarn io.javalin.http.Context
-dontwarn io.javalin.http.Handler
-dontwarn io.javalin.http.HandlerType
-dontwarn io.netty.internal.tcnative.AsyncSSLPrivateKeyMethod
-dontwarn io.netty.internal.tcnative.AsyncTask
-dontwarn io.netty.internal.tcnative.Buffer
-dontwarn io.netty.internal.tcnative.CertificateCallback
-dontwarn io.netty.internal.tcnative.CertificateCompressionAlgo
-dontwarn io.netty.internal.tcnative.CertificateVerifier
-dontwarn io.netty.internal.tcnative.Library
-dontwarn io.netty.internal.tcnative.ResultCallback
-dontwarn io.netty.internal.tcnative.SSL
-dontwarn io.netty.internal.tcnative.SSLContext
-dontwarn io.netty.internal.tcnative.SSLPrivateKeyMethod
-dontwarn io.netty.internal.tcnative.SSLSession
-dontwarn io.netty.internal.tcnative.SSLSessionCache
-dontwarn io.netty.internal.tcnative.SessionTicketKey
-dontwarn io.netty.internal.tcnative.SniHostNameMatcher
-dontwarn jakarta.servlet.http.HttpServletResponse
-dontwarn junit.framework.Assert
-dontwarn junit.framework.TestCase
-dontwarn lzma.sdk.lzma.Base
-dontwarn lzma.sdk.lzma.Encoder
-dontwarn net.jpountz.lz4.LZ4Compressor
-dontwarn net.jpountz.lz4.LZ4Exception
-dontwarn net.jpountz.lz4.LZ4Factory
-dontwarn net.jpountz.lz4.LZ4FastDecompressor
-dontwarn net.jpountz.xxhash.XXHash32
-dontwarn net.jpountz.xxhash.XXHashFactory
-dontwarn org.apache.log4j.Level
-dontwarn org.apache.log4j.Logger
-dontwarn org.apache.logging.log4j.Level
-dontwarn org.apache.logging.log4j.LogManager
-dontwarn org.apache.logging.log4j.Logger
-dontwarn org.apache.logging.log4j.spi.ExtendedLogger
-dontwarn org.apache.logging.log4j.spi.ExtendedLoggerWrapper
-dontwarn org.conscrypt.AllocatedBuffer
-dontwarn org.conscrypt.BufferAllocator
-dontwarn org.conscrypt.Conscrypt
-dontwarn org.conscrypt.Conscrypt$ProviderBuilder
-dontwarn org.conscrypt.HandshakeListener
-dontwarn org.eclipse.jetty.alpn.ALPN
-dontwarn org.eclipse.jetty.alpn.ALPN$ClientProvider
-dontwarn org.eclipse.jetty.alpn.ALPN$Provider
-dontwarn org.eclipse.jetty.alpn.ALPN$ServerProvider
-dontwarn org.eclipse.jetty.npn.NextProtoNego
-dontwarn org.eclipse.jetty.npn.NextProtoNego$ClientProvider
-dontwarn org.eclipse.jetty.npn.NextProtoNego$Provider
-dontwarn org.eclipse.jetty.npn.NextProtoNego$ServerProvider
-dontwarn org.immutables.value.Generated
-dontwarn org.immutables.value.Value
-dontwarn org.immutables.value.Value$Default
-dontwarn org.immutables.value.Value$Immutable
-dontwarn org.immutables.value.Value$Style
-dontwarn org.immutables.value.Value$Style$ImplementationVisibility
-dontwarn org.jboss.marshalling.ByteInput
-dontwarn org.jboss.marshalling.ByteOutput
-dontwarn org.jboss.marshalling.Marshaller
-dontwarn org.jboss.marshalling.MarshallerFactory
-dontwarn org.jboss.marshalling.MarshallingConfiguration
-dontwarn org.jboss.marshalling.Unmarshaller
-dontwarn org.jetbrains.annotations.Async
-dontwarn org.jetbrains.annotations.Async$Execute
-dontwarn org.jetbrains.annotations.Async$Schedule
-dontwarn org.jetbrains.annotations.VisibleForTesting
-dontwarn org.objectweb.asm.AnnotationVisitor
-dontwarn org.objectweb.asm.Attribute
-dontwarn org.objectweb.asm.ClassReader
-dontwarn org.objectweb.asm.ClassVisitor
-dontwarn org.objectweb.asm.ClassWriter
-dontwarn org.objectweb.asm.FieldVisitor
-dontwarn org.objectweb.asm.Label
-dontwarn org.objectweb.asm.MethodVisitor
-dontwarn org.objectweb.asm.Opcodes
-dontwarn org.objectweb.asm.Type
-dontwarn org.tukaani.xz.ARMOptions
-dontwarn org.tukaani.xz.ARMThumbOptions
-dontwarn org.tukaani.xz.DeltaOptions
-dontwarn org.tukaani.xz.FilterOptions
-dontwarn org.tukaani.xz.FinishableOutputStream
-dontwarn org.tukaani.xz.FinishableWrapperOutputStream
-dontwarn org.tukaani.xz.IA64Options
-dontwarn org.tukaani.xz.LZMA2InputStream
-dontwarn org.tukaani.xz.LZMA2Options
-dontwarn org.tukaani.xz.LZMAInputStream
-dontwarn org.tukaani.xz.LZMAOutputStream
-dontwarn org.tukaani.xz.MemoryLimitException
-dontwarn org.tukaani.xz.PowerPCOptions
-dontwarn org.tukaani.xz.SPARCOptions
-dontwarn org.tukaani.xz.SingleXZInputStream
-dontwarn org.tukaani.xz.UnsupportedOptionsException
-dontwarn org.tukaani.xz.X86Options
-dontwarn org.tukaani.xz.XZ
-dontwarn org.tukaani.xz.XZInputStream
-dontwarn org.tukaani.xz.XZOutputStream
-dontwarn reactor.blockhound.BlockHound
-dontwarn reactor.blockhound.BlockHound$Builder
-dontwarn reactor.blockhound.integration.BlockHoundIntegration

-dontwarn io.netty.**
-dontwarn org.apache.commons.compress.**
-dontwarn org.koin.core.time.**

##---------------Begin: proguard configuration for Gson  ----------
# Gson uses generic type information stored in a class file when working with fields. Proguard
# removes such information by default, so configure it to keep all of it.
-keepattributes Signature

# For using GSON @Expose annotation
-keepattributes *Annotation*

# Gson specific classes
-dontwarn sun.misc.**
#-keep class com.google.gson.stream.** { *; }

# Application classes that will be serialized/deserialized over Gson
-keep class com.google.gson.examples.android.model.** { <fields>; }

# Prevent proguard from stripping interface information from TypeAdapter, TypeAdapterFactory,
# JsonSerializer, JsonDeserializer instances (so they can be used in @JsonAdapter)
-keep class * extends com.google.gson.TypeAdapter
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer

# Prevent R8 from leaving Data object members always null
-keepclassmembers,allowobfuscation class * {
  @com.google.gson.annotations.SerializedName <fields>;
}

# Retain generic signatures of TypeToken and its subclasses with R8 version 3.0 and higher.
-keep,allowobfuscation,allowshrinking class com.google.gson.reflect.TypeToken
-keep,allowobfuscation,allowshrinking class * extends com.google.gson.reflect.TypeToken

##---------------End: proguard configuration for Gson  ----------
