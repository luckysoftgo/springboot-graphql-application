1.可参考的样例
    https://github.com/Netflix/dgs-framework
    https://netflix.github.io/dgs/mutations/

2.springboot-graphql-springboot-api
    基础的应用

3.springboot-graphql-netflix-dgs-api
    高级的应用

4.相关注解说明：
    @DgsQuery(field = "xxx")：对应 type Query 查询字段（读）
    @DgsMutation(field = "xxx")：对应 type Mutation 修改字段（写）
    @DgsSubscription(field = "xxx")：对应订阅实时推送
