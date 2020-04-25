#### Android-EventConflict
##### ViewPager与HorizontalScrollVIew冲突解决
- 这个Demo主要是解决ViewPager+Fragment组合时, Fragment中含有HorizontalScrollView控件,当HorizontalScrollView水平滑动到无法滑动时自动请求父容器拦截事件,然后触发ViewPager的滑动切换界面机制.