terraform {
  required_providers {
    clevercloud = {
      source = "clevercloud/clevercloud"
      version = "0.9.0"
    }
  }
}

provider "clevercloud" {
  # Configuration options
  organisation = var.organization_id
}

resource "clevercloud_fsbucket" "fs_filebrowser_01" {
  name   = "fs-filebrowser-01"
  region = var.region
}

resource "clevercloud_java_war" "spring_filebrowser_01" {
  name                = "spring-filebrowser-01"
  region              = var.region
  min_instance_count  = 1
  max_instance_count  = 1
  smallest_flavor     = "XS"
  biggest_flavor      = "XS"
  dependencies        = [
    clevercloud_fsbucket.fs_filebrowser_01.id
  ]

  deployment {
    repository = "https://github.com/cpontet/spring-filebrowser.git"
  }
  environment = {
    CC_JAVA_VERSION        = "21"
    MAVEN_DEPLOY_GOAL      = "spring-boot:run"
    FOLDER_PATH             = var.folder_path
    CC_FS_BUCKET           = "${var.folder_path}:${clevercloud_fsbucket.fs_filebrowser_01.host}"
  }
  depends_on = [ 
    clevercloud_fsbucket.fs_filebrowser_01 
  ]
}

resource "clevercloud_java_war" "spring_filebrowser_02" {
  name                = "spring-filebrowser-02"
  region              = var.region
  min_instance_count  = 1
  max_instance_count  = 1
  smallest_flavor     = "XS"
  biggest_flavor      = "XS"
  dependencies        = [
    clevercloud_fsbucket.fs_filebrowser_01.id
  ]

  deployment {
    repository = "https://github.com/cpontet/spring-filebrowser.git"
  }
  environment = {
    CC_JAVA_VERSION        = "21"
    MAVEN_DEPLOY_GOAL      = "spring-boot:run"
    FOLDER_PATH             = var.folder_path
    CC_FS_BUCKET           = "${var.folder_path}:${clevercloud_fsbucket.fs_filebrowser_01.host}"
  }
  depends_on = [ 
    clevercloud_fsbucket.fs_filebrowser_01 
  ]
}

