variable "organization_id" {
  description = "Clever Cloud organization ID. Treat as secret."
  type        = string
  sensitive   = true
}

variable "region" {
  description = "Clever Cloud region to deploy resources."
  type        = string
  default     = "par"
}

variable "folder_path" {
  description = "Path to files directory."
  type        = string
  default     = "/myfolder"
}
