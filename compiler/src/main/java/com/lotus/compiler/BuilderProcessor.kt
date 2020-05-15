package com.lotus.compiler

import com.bennyhuo.aptutils.AptContext
import com.bennyhuo.aptutils.logger.Logger
import com.bennyhuo.aptutils.types.isSubTypeOf
import com.lotus.annotations.Builder
import com.lotus.annotations.Optional
import com.lotus.annotations.Required
import com.lotus.compiler.activity.ActivityClass
import com.lotus.compiler.activity.entity.Field
import com.sun.tools.javac.code.Symbol
import java.lang.Exception
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.ProcessingEnvironment
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.SourceVersion
import javax.lang.model.element.Element
import javax.lang.model.element.ElementKind
import javax.lang.model.element.TypeElement


/**
 * :app:kaptGenerateStubsDebugKotlin 是将 kotlin 代码转成java d代码 目录  build/temp/stubs/debug
 */
class BuilderProcessor : AbstractProcessor() {

    private val supportedAnnotations = setOf(Builder::class.java, Optional::class.java, Required::class.java)

    override fun getSupportedSourceVersion()  = SourceVersion.RELEASE_8

    override fun getSupportedAnnotationTypes() = supportedAnnotations.mapTo(HashSet<String>(),Class<*>::getCanonicalName)

    override fun init(processingEnv: ProcessingEnvironment) {
        super.init(processingEnv)
        AptContext.init(processingEnv)

    }
    override fun process(annotations: MutableSet<out TypeElement>, env: RoundEnvironment): Boolean {

        val activityClasses = HashMap<Element,ActivityClass>()
        env.getElementsAnnotatedWith(Builder::class.java).filter {
            it.kind.isClass
        }.forEach{ element ->
            try{
                if(element.asType().isSubTypeOf("android.app.Activity")){
                    activityClasses[element] = ActivityClass(element as TypeElement)
                }else{
                    Logger.error(element,"Unsupported typeElement:${element.simpleName}")
                }
            }catch (e:Exception){
                Logger.logParsingError(element,Builder::class.java,e)
            }

            env.getElementsAnnotatedWith(Required::class.java).filter {
                it.kind == ElementKind.FIELD
            }.forEach { element ->
//                Logger.warn(it.toString())
                activityClasses[element.enclosingElement]?.fields?.add(Field(element as Symbol.VarSymbol))
//                Logger.warn(activityClasses[element.enclosingElement]?.fields?.toString())
//                        ?:Logger.error(element,"Field $element annotated as Required while ${element.enclosingElement} not annotation.")
            }

            env.getElementsAnnotatedWith(Optional::class.java).filter {
                it.kind == ElementKind.FIELD
            }.forEach { element ->
                activityClasses[element.enclosingElement]?.fields?.add(Field(element as Symbol.VarSymbol))
//                        ?:Logger.error(element,"Field $element annotated as Required while ${element.enclosingElement} not annotation.")
            }

//            activityClasses.values.forEach {
//                Logger.warn(it.toString())
//            }
//            Logger.warn(it,"小爷到此一游${it.simpleName.toString()} ")
        }
        return true
    }

}