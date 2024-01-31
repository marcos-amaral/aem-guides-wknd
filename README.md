# Persisted Query
A query pode receber uma lista de authorfirstNames e retorna os paths dos CFs cujo aitor tem esse primeiro nome
```
query getArticleByAuthor($authorFirstName: ArticleModelFilter) {
  articleList(
    sort: "title DESC"
    filter: $authorFirstName
    ) {
    items {
      _path
    }
  }
}
```
## Params
{
  "authorFirstName": 
  {
    "authorFragment": 
    {
    	"firstName": 
      {
        "_logOp": "OR",
        "_expressions": [{"value": "Justin"},{"value": "Sofia"}]
      }
  	}
  }
}

## Params URL encoded
%7B%22authorFirstName%22%3A%7B%22authorFragment%22%3A%7B%22firstName%22%3A%7B%22_logOp%22%3A%22OR%22%2C%22_expressions%22%3A%5B%7B%22value%22%3A%22Justin%22%7D%2C%7B%22value%22%3A%22Sofia%22%7D%5D%7D%7D%7D%7D

## URL query
http://localhost:4502/graphql/execute.json/wknd-shared/article-by-author-list%3B%7B%22authorFirstName%22%3A%7B%22authorFragment%22%3A%7B%22firstName%22%3A%7B%22_logOp%22%3A%22OR%22%2C%22_expressions%22%3A%5B%7B%22value%22%3A%22Justin%22%7D%2C%7B%22value%22%3A%22Sofia%22%7D%5D%7D%7D%7D%7D
