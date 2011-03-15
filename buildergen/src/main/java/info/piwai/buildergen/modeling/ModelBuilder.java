package info.piwai.buildergen.modeling;

import static com.sun.codemodel.JExpr._this;
import info.piwai.buildergen.api.Buildable;
import info.piwai.buildergen.api.Builder;
import info.piwai.buildergen.helper.ElementHelper;

import java.util.List;
import java.util.Set;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;

import com.sun.codemodel.JBlock;
import com.sun.codemodel.JClass;
import com.sun.codemodel.JClassAlreadyExistsException;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JFieldVar;
import com.sun.codemodel.JInvocation;
import com.sun.codemodel.JMethod;
import com.sun.codemodel.JMod;
import com.sun.codemodel.JVar;

public class ModelBuilder {

	private final ElementHelper elementHelper;

	public ModelBuilder(ElementHelper elementHelper) {
		this.elementHelper = elementHelper;
	}

	public JCodeModel build(Set<TypeElement> validatedElements) throws JClassAlreadyExistsException {
		JCodeModel codeModel = new JCodeModel();
		for (TypeElement buildableElement : validatedElements) {
			String builderFullyQualifiedName = extractBuilderFullyQualifiedName(buildableElement);

			Set<ExecutableElement> constructors = elementHelper.findAccessibleConstructors(buildableElement);

			ExecutableElement constructor = elementHelper.findBuilderConstructor(constructors);

			JDefinedClass builderClass = codeModel._class(builderFullyQualifiedName);

			JClass buildableClass = codeModel.ref(buildableElement.getQualifiedName().toString());

			JClass builderInterface = codeModel.ref(Builder.class);
			JClass narrowedInterface = builderInterface.narrow(buildableClass);

			builderClass._implements(narrowedInterface);

			List<? extends VariableElement> parameters = constructor.getParameters();
			for (VariableElement parameter : parameters) {
				String paramName = parameter.getSimpleName().toString();
				String paramClassFullyQualifiedName = parameter.asType().toString();
				JClass paramClass = codeModel.ref(paramClassFullyQualifiedName);
				JFieldVar setterField = builderClass.field(JMod.PRIVATE, paramClass, paramName);

				JMethod setter = builderClass.method(JMod.PUBLIC, builderClass, paramName);
				JVar setterParam = setter.param(paramClass, paramName);
				setter.body() //
						.assign(_this().ref(setterField), setterParam) //
						._return(_this());
			}

			JMethod buildMethod = builderClass.method(JMod.PUBLIC, buildableClass, "build");

			List<? extends TypeMirror> thrownTypes = constructor.getThrownTypes();
			for (TypeMirror thrownType : thrownTypes) {
				JClass thrownClass = codeModel.ref(thrownType.toString());
				buildMethod._throws(thrownClass);
			}

			JBlock buildBody = buildMethod.body();
			JInvocation newBuildable = JExpr._new(buildableClass);

			for (VariableElement parameter : constructor.getParameters()) {
				String paramName = parameter.getSimpleName().toString();
				newBuildable.arg(JExpr.ref(paramName));
			}

			buildBody._return(newBuildable);

			JMethod createMethod = builderClass.method(JMod.PUBLIC | JMod.STATIC, builderClass, "create");
			createMethod.body()._return(JExpr._new(builderClass));

		}
		return codeModel;
	}

	private String extractBuilderFullyQualifiedName(TypeElement buildableElement) {
		Buildable buildableAnnotation = buildableElement.getAnnotation(Buildable.class);
		String builderSuffix = buildableAnnotation.value();

		String buildableFullyQualifiedName = buildableElement.getQualifiedName().toString();

		return buildableFullyQualifiedName + builderSuffix;
	}

}
