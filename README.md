#Example of AMP integration 

This example is based on news content template of [dx-base-demo-templates](https://github.com/Jahia/dx-base-demo-templates)

## what's inside ? 
- A dependency to the news module.
- A content template "news detail AMP" of jnt:news named "amp"

## the views
- The template view is based on the [basic AMP markup](https://www.ampproject.org/docs/get_started/create/basic_markup) and set as the view of the content 
template. Its name is **bootstrap-acme-space-amp**. It only contains a main resource display using **detail-amp**.
- The news view is named **detail-amp** and is based on the the 
[detail view](https://github.com/Jahia/bootstrap-acme-space-templates/blob/master/src/main/resources/jnt_news/html/news.detail.jsp)
 of the news 