# Generated by jeweler
# DO NOT EDIT THIS FILE DIRECTLY
# Instead, edit Jeweler::Tasks in Rakefile, and run the gemspec command
# -*- encoding: utf-8 -*-

Gem::Specification.new do |s|
  s.name = %q{activerecord-oracle_enhanced-adapter}
  s.version = "1.3.0"

  s.required_rubygems_version = Gem::Requirement.new(">= 0") if s.respond_to? :required_rubygems_version=
  s.authors = ["Raimonds Simanovskis"]
  s.date = %q{2010-06-21}
  s.description = %q{Oracle "enhanced" ActiveRecord adapter contains useful additional methods for working with new and legacy Oracle databases.
This adapter is superset of original ActiveRecord Oracle adapter.
}
  s.email = %q{raimonds.simanovskis@gmail.com}
  s.extra_rdoc_files = [
    "README.rdoc"
  ]
  s.files = [
    ".gitignore",
     "History.txt",
     "License.txt",
     "README.rdoc",
     "Rakefile",
     "VERSION",
     "activerecord-oracle_enhanced-adapter.gemspec",
     "lib/active_record/connection_adapters/emulation/oracle_adapter.rb",
     "lib/active_record/connection_adapters/oracle_enhanced.rake",
     "lib/active_record/connection_adapters/oracle_enhanced_activerecord_patches.rb",
     "lib/active_record/connection_adapters/oracle_enhanced_adapter.rb",
     "lib/active_record/connection_adapters/oracle_enhanced_connection.rb",
     "lib/active_record/connection_adapters/oracle_enhanced_context_index.rb",
     "lib/active_record/connection_adapters/oracle_enhanced_core_ext.rb",
     "lib/active_record/connection_adapters/oracle_enhanced_cpk.rb",
     "lib/active_record/connection_adapters/oracle_enhanced_dirty.rb",
     "lib/active_record/connection_adapters/oracle_enhanced_jdbc_connection.rb",
     "lib/active_record/connection_adapters/oracle_enhanced_oci_connection.rb",
     "lib/active_record/connection_adapters/oracle_enhanced_procedures.rb",
     "lib/active_record/connection_adapters/oracle_enhanced_schema_definitions.rb",
     "lib/active_record/connection_adapters/oracle_enhanced_schema_dumper.rb",
     "lib/active_record/connection_adapters/oracle_enhanced_schema_statements_ext.rb",
     "lib/active_record/connection_adapters/oracle_enhanced_tasks.rb",
     "lib/active_record/connection_adapters/oracle_enhanced_version.rb",
     "spec/active_record/connection_adapters/oracle_enhanced_adapter_spec.rb",
     "spec/active_record/connection_adapters/oracle_enhanced_adapter_structure_dumper_spec.rb",
     "spec/active_record/connection_adapters/oracle_enhanced_connection_spec.rb",
     "spec/active_record/connection_adapters/oracle_enhanced_context_index_spec.rb",
     "spec/active_record/connection_adapters/oracle_enhanced_core_ext_spec.rb",
     "spec/active_record/connection_adapters/oracle_enhanced_cpk_spec.rb",
     "spec/active_record/connection_adapters/oracle_enhanced_data_types_spec.rb",
     "spec/active_record/connection_adapters/oracle_enhanced_dbms_output_spec.rb",
     "spec/active_record/connection_adapters/oracle_enhanced_dirty_spec.rb",
     "spec/active_record/connection_adapters/oracle_enhanced_emulate_oracle_adapter_spec.rb",
     "spec/active_record/connection_adapters/oracle_enhanced_procedures_spec.rb",
     "spec/active_record/connection_adapters/oracle_enhanced_schema_dump_spec.rb",
     "spec/active_record/connection_adapters/oracle_enhanced_schema_spec.rb",
     "spec/spec.opts",
     "spec/spec_helper.rb"
  ]
  s.homepage = %q{http://github.com/rsim/oracle-enhanced}
  s.rdoc_options = ["--charset=UTF-8"]
  s.require_paths = ["lib"]
  s.rubygems_version = %q{1.3.7}
  s.summary = %q{Oracle enhanced adapter for ActiveRecord}
  s.test_files = [
    "spec/active_record/connection_adapters/oracle_enhanced_adapter_spec.rb",
     "spec/active_record/connection_adapters/oracle_enhanced_adapter_structure_dumper_spec.rb",
     "spec/active_record/connection_adapters/oracle_enhanced_connection_spec.rb",
     "spec/active_record/connection_adapters/oracle_enhanced_context_index_spec.rb",
     "spec/active_record/connection_adapters/oracle_enhanced_core_ext_spec.rb",
     "spec/active_record/connection_adapters/oracle_enhanced_cpk_spec.rb",
     "spec/active_record/connection_adapters/oracle_enhanced_data_types_spec.rb",
     "spec/active_record/connection_adapters/oracle_enhanced_dbms_output_spec.rb",
     "spec/active_record/connection_adapters/oracle_enhanced_dirty_spec.rb",
     "spec/active_record/connection_adapters/oracle_enhanced_emulate_oracle_adapter_spec.rb",
     "spec/active_record/connection_adapters/oracle_enhanced_procedures_spec.rb",
     "spec/active_record/connection_adapters/oracle_enhanced_schema_dump_spec.rb",
     "spec/active_record/connection_adapters/oracle_enhanced_schema_spec.rb",
     "spec/spec_helper.rb"
  ]

  if s.respond_to? :specification_version then
    current_version = Gem::Specification::CURRENT_SPECIFICATION_VERSION
    s.specification_version = 3

    if Gem::Version.new(Gem::VERSION) >= Gem::Version.new('1.2.0') then
      s.add_development_dependency(%q<rspec>, [">= 1.3.0"])
    else
      s.add_dependency(%q<rspec>, [">= 1.3.0"])
    end
  else
    s.add_dependency(%q<rspec>, [">= 1.3.0"])
  end
end

