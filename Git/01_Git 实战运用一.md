### Git 实战运用一

- 第一种情况是本地已经有了git仓库，做了一些开发工作：

  - 首先在Github上新建一个repository

  - 在本地Git仓库目录下，为当前目录添加remote：

    `git remote add github git@github.com:XiangBicheng/math.git`

  - 将本地仓库master分支的代码push到GitHub，第一次往Github上push的时候，需要设置`-u`，即--set-upstream，之后就可以直接使用`git push github master` 了：

    `git push -u github master` 

- 注意：如果你的电脑里用户目录下没有设置SSH，第一次的时候需要设置一下：

  - 在git shell下，键入：`sshkey-gen -t rsa -C "xiangbcyx@qq.com"`
  - 在用户目录下，找到.ssh文件夹，找到里面的公钥，添加至Github

- 在Github上，有很多的repository，当你想contribute某个项目时，你首先需要fork该项目。fork就相当于在你的Github上复制了一个很原repo相同的代码库，并且Github知道你是从哪个repo复制的。fork过后，你就可以把该项目的代码git clone到本地，做相关的开发测试，然后push到你自己fork得到的那个repo上。当你对你的fork repo做了更新时，Github会跟踪到这些更新，并且显示你ahead 原来的仓库多少个commits。这时，你就可以在你的fork页面，new pull request，请求对方接收你的代码了。当然，pull request也可以被关闭。