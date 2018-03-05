# Backend

## Services
Services are automatically loaded onto their URL with @Path annotation. The class ApiApplication uses reflection to initialize all services needed. Also any filter is automatically loaded when using the @Filter annotation. ExceptionMappers are automatically loaded with the @EH annotation (ExceptionHandler).

## MethodInterception - Database connection
I define a DataSource resource inside the context.xml. Within the code, any method annotated with @InjectContext (class implements IService) will have the context automatically injected when calling the function.
