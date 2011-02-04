require File.expand_path(File.dirname(__FILE__) + '/../../spec_helper')
require 'yaml'

module Cucumber
  module Cli
    describe Main do
      before(:each) do
        @out = StringIO.new
        @err = StringIO.new
        Kernel.stub!(:exit).and_return(nil)
        File.stub!(:exist?).and_return(false) # When Configuration checks for cucumber.yml
        Dir.stub!(:[]).and_return([]) # to prevent cucumber's features dir to being laoded
      end

      describe "verbose mode" do

        before(:each) do
          @empty_feature = Cucumber::Ast::Feature.new(nil, Cucumber::Ast::Comment.new(''), Cucumber::Ast::Tags.new(2, []), "Feature", "Foo", [])
        end

        it "should show feature files parsed" do
          @cli = Main.new(%w{--verbose example.feature}, @out)
          @cli.stub!(:require)

          Cucumber::FeatureFile.stub!(:new).and_return(mock("feature file", :parse => @empty_feature))

          @cli.execute!

          @out.string.should include('example.feature')
        end

      end

      describe "--format with class" do

       describe "in module" do

          it "should resolve each module until it gets Formatter class" do
            cli = Main.new(%w{--format ZooModule::MonkeyFormatterClass}, nil)
            mock_module = mock('module')
            Object.stub!(:const_defined?).and_return(true)
            mock_module.stub!(:const_defined?).and_return(true)

            f = stub('formatter').as_null_object

            Object.should_receive(:const_get).with('ZooModule').and_return(mock_module)
            mock_module.should_receive(:const_get).with('MonkeyFormatterClass').and_return(mock('formatter class', :new => f))

            cli.execute!
          end

        end
      end

      [ProfilesNotDefinedError, YmlLoadError, ProfileNotFound].each do |exception_klass|

        it "rescues #{exception_klass}, prints the message to the error stream and returns true" do
          Configuration.stub!(:new).and_return(configuration = mock('configuration'))
          configuration.stub!(:parse!).and_raise(exception_klass.new("error message"))

          main = Main.new('', out = StringIO.new, error = StringIO.new)
          main.execute!.should be_true
          error.string.should == "error message\n"
        end
      end

      context "--drb" do
        before(:each) do
          @configuration = mock('Configuration', :drb? => true).as_null_object
          Configuration.stub!(:new).and_return(@configuration)

          @args = ['features']

          @cli = Main.new(@args, @out, @err)
          @step_mother = mock('StepMother').as_null_object
          StepMother.stub!(:new).and_return(@step_mother)
        end

        it "delegates the execution to the DRB client passing the args and streams" do
          @configuration.stub :drb_port => 1450
          DRbClient.should_receive(:run).with(@args, @err, @out, 1450).and_return(true)
          @cli.execute!
        end

        it "returns the result from the DRbClient" do
          DRbClient.stub!(:run).and_return('foo')
          @cli.execute!.should == 'foo'
        end

        it "ceases execution if the DrbClient is able to perform the execution" do
          DRbClient.stub!(:run).and_return(true)
          @configuration.should_not_receive(:build_formatter_broadcaster)
          @cli.execute!
        end

        context "when the DrbClient is unable to perfrom the execution" do
          before { DRbClient.stub!(:run).and_raise(DRbClientError.new('error message.')) }

          it "alerts the user that execution will be performed locally" do
            @cli.execute!
            @err.string.should include("WARNING: error message. Running features locally:")
          end

        end
      end
    end
  end
end
